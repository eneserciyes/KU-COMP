#ifndef P2
#define P2

#include <pthread.h>
#include <stdint.h>  // uint32
#include <sys/time.h>

#include <cstdlib>
#include <iostream>
#include <vector>

#include "Commentator.h"
#include "LockQueue.h"
#include "util.h"

/**
 * This is the main header file where all important variables and methods are
 * declared. It uses a LockQueue which is an abstraction of a queue structure
 * that enables concurrent access with mutex locks.
 * @author Mehmet Enes Erciyes (merciyes18) - Nursena Köprücü (nkoprucu16)
 */

// Alias interface for mutex lock/unlock

void wait(pthread_mutex_t& mutex) { pthread_mutex_lock(&mutex); }
void signal(pthread_mutex_t& mutex) { pthread_mutex_unlock(&mutex); }

// Global variables
LockQueue queue;
double n, p, q, t, b;
double question_count;

Logger logger;

// Locks
std::vector<pthread_mutex_t> commentator_floor;
std::vector<pthread_mutex_t> commentator_finished;

std::vector<pthread_mutex_t> responses;
std::vector<pthread_mutex_t> question_asked;
std::vector<pthread_mutex_t> commentator_created;

pthread_mutex_t speaking_lock;
pthread_mutex_t listening_lock;  // used for waiting the finish of all speakers
                                 // before the next question is asked
pthread_mutex_t question_count_lock;
pthread_mutex_t log_lock;

pthread_cond_t breaking_event_cond;
pthread_mutex_t breaking_dummy_lock;

// Functions

std::vector<pthread_t> create_commentator_threads(int n);
pthread_t create_moderator_thread();
pthread_t create_breaking_events_thread();

void* commentate(void* args);
void* moderate(void* args);
void* breaking_event_generator(void* args);

// Question count interface with concurrent access
void decrement_question_count() {
  pthread_mutex_lock(&question_count_lock);
  question_count--;
  pthread_mutex_unlock(&question_count_lock);
}

int get_question_count() {
  int val;
  pthread_mutex_lock(&question_count_lock);
  val = question_count;
  pthread_mutex_unlock(&question_count_lock);
  return val;
}

// Initializing the locker and time for logging
void init_logger() {
  struct timeval tp;
  gettimeofday(&tp, NULL);
  logger = Logger(tp);
}

// Initializing locks

void init_locks() {
  for (int i = 0; i < n; i++) {
    pthread_mutex_t floor, finish, response, question, create;

    pthread_mutex_init(&floor, NULL);
    pthread_mutex_init(&finish, NULL);
    pthread_mutex_init(&response, NULL);
    pthread_mutex_init(&question, NULL);
    pthread_mutex_init(&create, NULL);

    // initially lock all floor, response and question locks to prevent speaking
    // without moderator granting permission
    pthread_mutex_lock(&floor);
    pthread_mutex_lock(&finish);
    pthread_mutex_lock(&response);
    pthread_mutex_lock(&question);
    pthread_mutex_lock(&create);

    commentator_floor.push_back(floor);
    commentator_finished.push_back(finish);

    responses.push_back(response);
    question_asked.push_back(question);
    commentator_created.push_back(create);
  }

  // Should not lock these at the beginning.
  pthread_mutex_init(&speaking_lock, NULL);
  pthread_mutex_init(&listening_lock, NULL);
  pthread_mutex_init(&question_count_lock, NULL);
  pthread_mutex_init(&log_lock, NULL);

  // Breaking event
  breaking_event_cond = PTHREAD_COND_INITIALIZER;
  breaking_dummy_lock = PTHREAD_MUTEX_INITIALIZER;
}

/**
 * Original pthread_sleep function provided
 * original by Yingwu Zhu
 * updated by Muhammed Nufail Farooqi
 * updated by Fahrican Kosar
 */
int pthread_sleep(double seconds) {
  pthread_mutex_t mutex;
  pthread_cond_t conditionvar;
  if (pthread_mutex_init(&mutex, NULL)) {
    return -1;
  }
  if (pthread_cond_init(&conditionvar, NULL)) {
    return -1;
  }

  struct timeval tp;
  struct timespec timetoexpire;
  // When to expire is an absolute time, so get the current time and add
  // it to our delay time
  gettimeofday(&tp, NULL);
  long new_nsec = tp.tv_usec * 1000 + (seconds - (long)seconds) * 1e9;
  timetoexpire.tv_sec = tp.tv_sec + (long)seconds + (new_nsec / (long)1e9);
  timetoexpire.tv_nsec = new_nsec % (long)1e9;

  pthread_mutex_lock(&mutex);
  int res = pthread_cond_timedwait(&conditionvar, &mutex, &timetoexpire);
  pthread_mutex_unlock(&mutex);
  pthread_mutex_destroy(&mutex);
  pthread_cond_destroy(&conditionvar);

  // Upon successful completion, a value of zero shall be returned
  return res;
}

/**
 * pthread_sleep takes an integer number of seconds to pause the current thread
 * original by Yingwu Zhu
 * updated by Muhammed Nufail Farooqi
 * updated by Fahrican Kosar
 * updated by Enes Erciyes
 *
 * Updated to use a global cond_t for implementing part 2
 */
int pthread_sleep_updated(double seconds) {
  struct timeval tp;
  struct timespec timetoexpire;
  // When to expire is an absolute time, so get the current time and add
  // it to our delay time
  gettimeofday(&tp, NULL);
  long new_nsec = tp.tv_usec * 1000 + (seconds - (long)seconds) * 1e9;
  timetoexpire.tv_sec = tp.tv_sec + (long)seconds + (new_nsec / (long)1e9);
  timetoexpire.tv_nsec = new_nsec % (long)1e9;

  pthread_mutex_lock(&breaking_dummy_lock);
  int res = pthread_cond_timedwait(&breaking_event_cond, &breaking_dummy_lock,
                                   &timetoexpire);
  pthread_mutex_unlock(&breaking_dummy_lock);

  // Upon successful completion, a value of zero shall be returned
  return res;
}

#endif