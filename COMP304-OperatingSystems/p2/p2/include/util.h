#ifndef UTIL
#define UTIL

#include <chrono>
#include <iostream>
#include <string>

#include "pthread.h"

struct Logger {
  pthread_mutex_t log_lock;
  struct timeval start;

 private:
  std::string pad(int number, int pad_limit){
    std::string result = std::to_string(number);
    int pad_count = pad_limit - result.size();
    for(int i=0; i<pad_count; i++){
      result = "0"+result;
    }
    return result;
  }

 public:
  Logger() {}
  Logger(struct timeval tp) {
    log_lock = PTHREAD_MUTEX_INITIALIZER;
    start = tp;
  }

  void timestamp() {
    struct timeval t_now;
    struct timeval elapsed;
    gettimeofday(&t_now, NULL);

    timersub(&t_now, &start, &elapsed);

    pthread_mutex_lock(&log_lock);
    std::cout << "[" << pad(elapsed.tv_sec / 60, 2) << "::" << pad(elapsed.tv_sec % 60, 2)
              << "." << pad(elapsed.tv_usec / 1000, 3) << "] ";
    pthread_mutex_unlock(&log_lock);
  }

  template <typename T>
  Logger& operator<<(T const& msg) {
    pthread_mutex_lock(&log_lock);
    std::cout << msg;
    pthread_mutex_unlock(&log_lock);
    return *this;
  }
};

int parseFlags(int argc, char** argv, double& n, double& p, double& q,
               double& t, double& b) {
  if (argc != 11) {
    std::cout << "Invalid arguments. Usage: .. -n [number of commentators] -p "
                 "[probability of each commentator answering] -q [question "
                 "count] -t [time to comment] -b [breaking event probability]"
              << std::endl;
    return 1;
  }
  for (int i = 1; i < argc - 1; i += 2) {
    std::string flag(argv[i]);
    std::string value(argv[i + 1]);

    if (flag == "-n") {
      n = std::stod(value);
    } else if (flag == "-p") {
      p = std::stod(value);
    } else if (flag == "-q") {
      q = std::stod(value);
    } else if (flag == "-t") {
      t = std::stod(value);
    } else if (flag == "-b") {
      b = std::stod(value);
    } else {
      std::cout << "Invalid flag passed!" << std::endl;
      return 1;
    }
  }
  return 0;
}

bool prob(double p) {
  double rnd = (double) rand() / RAND_MAX;
  return rnd <= p;
}

int get_now() {
  // auto now = std::chrono::system_clock::now();
  // auto seconds_elapsed = (now - start).count();
  // return seconds_elapsed;
  return 1;
}


#endif