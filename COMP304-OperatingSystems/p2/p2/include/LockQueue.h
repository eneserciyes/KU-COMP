
#include <pthread.h>

#include <queue>
#include <string>

#include "Commentator.h"

class LockQueue {
 private:
  std::deque<Commentator> queue;
  pthread_mutex_t lock{};
  int breaking_event = 0;

 public:
  void push(Commentator& commentator) {
    pthread_mutex_lock(&lock);
    queue.push_back(commentator);
    pthread_mutex_unlock(&lock);
  }

  Commentator& pop() {
    pthread_mutex_lock(&lock);
    Commentator& first = queue.front();
    queue.pop_front();
    pthread_mutex_unlock(&lock);
    return first;
  }

  bool empty() {
    pthread_mutex_lock(&lock);
    bool is_empty = queue.empty();
    pthread_mutex_unlock(&lock);
    return is_empty;
  }

  int size() {
    pthread_mutex_lock(&lock);
    int size = queue.size();
    pthread_mutex_unlock(&lock);
    return size;
  }

  void clear() {
    pthread_mutex_lock(&lock);
    for (auto& commentator : queue) {
      commentator.set_is_speaking(false);
      commentator.set_speak_time(-1);
    }
    queue.clear();
    pthread_mutex_unlock(&lock);
  }

  void set_breaking_event(int mode) {
    pthread_mutex_lock(&lock);
    breaking_event = mode;
    pthread_mutex_unlock(&lock);
  }

  int get_breaking_event() {
    int return_val;
    pthread_mutex_lock(&lock);
    return_val = breaking_event;
    pthread_mutex_unlock(&lock);
    return return_val;
  }

  LockQueue() { lock = PTHREAD_MUTEX_INITIALIZER; }
};
