;; constructor for points

(define first car)
(define (second x) (car (cdr x)))

(define make-point
  (lambda (x y) (list x y)))

;; selectors for points

(define x-of 
  (lambda (pt) (first pt)))

(define y-of
  (lambda (pt) (second pt)))

;; example points

(define p1 (make-point 0 0))
(define p2 (make-point 0 4))
(define p3 (make-point 2 5))
(define p4 (make-point 4 4))
(define p5 (make-point 6 2))
(define p6 (make-point 3 0))

(define test-points
  (list p1 p2 p3 p4 p5 p6))

;; constructor for segments

(define make-seg
  (lambda (p1 p2) (list p1 p2)))

;; selectors for segments

(define start-seg
  (lambda (seg) (first seg)))

(define end-seg
  (lambda (seg) (second seg)))

;; example segments

(define test-segments
  (list (make-seg p1 p2)
	(make-seg p2 p3)
	(make-seg p3 p4)
	(make-seg p4 p5)
	(make-seg p5 p6)
	(make-seg p6 p1)))


	