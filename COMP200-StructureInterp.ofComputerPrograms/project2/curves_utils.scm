;;;; CURVES_UTILS.SCM 
;;; code for 6.001 project 1, Fall 2003

;;; USEFUL, SIMPLE, GENERAL PROCEDURES

(define (compose f g)
  (lambda (x)
    (f (g x))))

(define (thrice f)
  (compose (compose f f) f))

(define (identity t) t)

(define (repeated f n)
  (if (= n 0)
      identity
      (compose f (repeated f (- n 1)))))

;;;  USEFUL NUMERICAL PROCEDURE

(define (square x) (* x x))


                       ;;;USEFUL ANGLES

(define pi/4 (atan 1 1))
(define pi (* 4 pi/4))
(define -pi (- pi))
(define 2pi (* 2 pi))


