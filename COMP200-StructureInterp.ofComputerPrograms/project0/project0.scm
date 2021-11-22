;;; merciyes18   14.10.2019
;;;
;;; Comp200 Project 0
;;;
;;; Note: The questions for this project are available at the course website.
;;;   Type all your work and notes in the appropriate sections of this file.
;;;   Please do not delete any of the existing lines.
;;;   Please do not make any modifications after midnight on the due date.
;;;   Semicolon is the Scheme comment character.


;_BANNER_
;;; 2.1 Preparing your project material for submission
#lang racket
(require racket/trace)
;_BANNER_
;;; 2.2 Expressions to Evaluate
-37
;Value : 37

(* 8 9)
;Value : 72

(> 10 9.7)
;Value : #t

(- (if (> 3 4)
       7
       10)
   (/ 16 10))
;Value : 8.4

(* (- 25 10)
   (+ 6 3))
;Value : 135

+
;Value : #<procedure:+>

(define double (lambda (x) (* 2 x)))
;Value : <procedure:double> = (number) => (number)  

double
;Value : _empty_

(define c 4)
;Value : _empty_

c
;Value : 4

(double c)
;Value : 8

c
;Value : 4

(double (double (+ c 5)))
;Value : 36

(define times-2 double)
;Value : _empty_

(times-2 c)
;Value : 8

(define d c)
;Value : _empty_

(= c d)
;Value : #t

(cond ((>= c 2) d)
      ((= c (- d 5)) (+ c d))
      (else (abs (- c d))))
;Value : 4

;_BANNER_
;;; 2.3 Pretty Printing
(define abs
  (lambda (a)
    (if (> a 0)
        a
        (- a))))

;_BANNER_
;;; 2.4 Tracing
(define (fun x)
  (if (zero? x)
    1
    (* x (fun (- x 1)))))

(trace fun)

(fun 4)

#|Value:
>(fun 4)
> (fun 3)
> >(fun 2)
> > (fun 1)
> > >(fun 0)
< < <1
< < 1
< <2
< 6
<24|#

;_BANNER_
;;; 2.5 Documentation and Administrative Questions

#|
1. When writing more complex programs, it becomes quite hard to keep track of
every code you wrote. Also, with more complex programs, it gets harder to trace the
steps and recursions. Therefore, tracing and debugging options allow us to see what
each procedure is doing step by step and step through the program line by line to
find where the bug occurs.

2.While most operations in Scheme are procedure calls there are a few other kinds of expressions which behave differently. They are called special forms. Some of the expressions, we calculated above, are special forms:

* (define double (lambda (x) (* 2 x)))
* (cond ((>= c 2) d)
      ((= c (- d 5)) (+ c d))
      (else (abs (- c d))))
* (define d c)

Other than these special forms, let,if,cons and many more are also special forms in Racket.

3.Racket identifiers are case-sensitive, but capital letters are rarely used. Hyphens (-) are preferred over underscores (_) in identifiers.

|#
