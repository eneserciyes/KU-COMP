#ifndef COMMENTATOR
#define COMMENTATOR

#include <ctime>

class Commentator {
 private:
  long id;
  double speak_time;
  bool is_speaking;

 public:
  Commentator(long _id) : id(_id), is_speaking(false){};
  long get_id() { return id; }

  double get_speak_time() { return speak_time; }
  void set_speak_time(double time) { speak_time = time; }

  bool get_is_speaking() { return is_speaking; }
  void set_is_speaking(bool _is_speaking) { is_speaking = _is_speaking; }
};
#endif