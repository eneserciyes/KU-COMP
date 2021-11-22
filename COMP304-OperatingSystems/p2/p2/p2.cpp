#include "p2.h"

/**
 * Main .cpp file. Function definitions and main function are here.
 * @author Mehmet Enes Erciyes (merciyes18) - Nursena Köprücü (nkoprucu16)
 */
pthread_t create_breaking_events_thread() {
  pthread_t thread;
  if (pthread_create(&thread, NULL, breaking_event_generator, NULL) != 0) {
    perror("Failed to create thread!");
  };
  return thread;
}

std::vector<pthread_t> create_commentator_threads(int n) {
  std::vector<pthread_t> threads;
  int args[n];

  for (int i = 0; i < n; i++) {
    pthread_t thread;
    args[i] = i;
    if (pthread_create(&thread, NULL, commentate, &args[i]) != 0) {
      perror("Failed to create thread!");
    };
    threads.push_back(thread);
  }

  return threads;
}

pthread_t create_moderator_thread() {
  pthread_t thread;
  pthread_create(&thread, NULL, moderate, 0);
  return thread;
}

void* breaking_event_generator(void* args) {
  while (true) {
    pthread_sleep(1);
    if (prob(b)) {
      queue.clear();
      queue.set_breaking_event(1);

      logger.timestamp();
      logger << "Breaking news!\n";
      pthread_cond_broadcast(
          &breaking_event_cond);  // signal commentators to be cut short!
      pthread_sleep(5);           // wait for 5 seconds of breaking events

      logger.timestamp();
      logger << "Breaking news ended!\n";
      queue.set_breaking_event(0);
    }
    // As all threads, break if all questions are asked.
    if (get_question_count() <= 0) {
      break;
    }
  }
  return 0;
}

void* commentate(void* args) {
  /**
   * Commentator thread function. Handles initialization, response to question
   * asked and the speaking
   * */
  int id = *(int*)args;
  // logger.timestamp();
  // logger << "Commentator " << id << " is created!\n";

  Commentator commentator = Commentator(id);  // a Commentator object holds

  signal(commentator_created[id]);

  while (true) {
    // As all threads, break if all questions are asked.
    if (get_question_count() <= 0) {
      break;
    }

    wait(question_asked[id]);  // Wait for a question to be asked
    // With probability p, make commentator speak for the question
    if (prob(p)) {
      double speak_time = rand() * t / RAND_MAX;
      commentator.set_speak_time(speak_time);
      commentator.set_is_speaking(true);
      queue.push(commentator);
      logger.timestamp();
      logger << "Commentator #" + std::to_string(commentator.get_id()) +
                    " generates an answer - Position in queue: " +
                    std::to_string(queue.size() - 1) + "\n";
    } else {
      commentator.set_is_speaking(false);
    }
    signal(responses[id]);  // signal the response

    if (commentator.get_is_speaking()) {  // if commentator is speaking
      wait(commentator_floor[id]);        // wait for the floor to come
      logger.timestamp();
      logger << "Commentator #" << id << "'s turn to speak for "
             << commentator.get_speak_time() << " seconds.\n";

      int result = pthread_sleep_updated(
          commentator.get_speak_time());  // Sleep for the previously saved

      commentator.set_is_speaking(false);
      if (result == ETIMEDOUT) {
        signal(
            commentator_finished[id]);  // When finished speaking signal
                                        // finished so that moderator can
                                        // continue with the next commentator.
        logger.timestamp();
        logger << "Commentator #" << id << " finished speaking\n";
      } else {
        // A breaking event occurred
        logger.timestamp();
        logger << "Commentator #" << id
               << " is cut short due to a breaking news!\n";
        signal(commentator_finished[id]);
      }
    }
  }
  return 0;
}

void* moderate(void* args) {
  while (true) {
    // As all threads, break if all questions are asked.
    if (get_question_count() <= 0) {
      break;
    }

    // If queue is empty, ask a new question
    if (!queue.get_breaking_event() && queue.empty()) {
      decrement_question_count();  // using this to access question value
      logger.timestamp();
      logger << "Moderator asks question #" << q - question_count << "\n";
      // Signal all question_asked locks
      for (auto& lock : question_asked) {
        signal(lock);
      }
      // Wait for the responses to arrive
      for (auto& lock : responses) {
        wait(lock);
      }
    }

    // If there are some things in the queue, give speaking permissions to
    // commentators until they are finished.
    while (!queue.empty()) {
      Commentator& commentator = queue.pop();
      signal(commentator_floor[commentator.get_id()]);   // release the floor
                                                         // lock for granting
                                                         // permission to
                                                         // commentator.
      wait(commentator_finished[commentator.get_id()]);  // wait until current
                                                         // commentator
                                                         // finishes talking
    }
  }
  return 0;
}

int main(int argc, char** argv) {
  srand(12);

  if (parseFlags(argc, argv, n, p, q, t, b)) return 1;
  question_count = q;

  init_locks();
  init_logger();

  std::vector<pthread_t> commentator_threads = create_commentator_threads(n);

  for (auto& lock : commentator_created) {
    wait(lock);  // wait for all threads to be created.
  }

  std::cout << "All commentators are created" << std::endl;

  pthread_t moderator_thread = create_moderator_thread();

  pthread_t breaking_events_thread = create_breaking_events_thread();

  pthread_join(moderator_thread, NULL);

  for (auto& thread : commentator_threads) {
    pthread_join(thread, NULL);
  }

  std::cout << "All finished" << std::endl;
}