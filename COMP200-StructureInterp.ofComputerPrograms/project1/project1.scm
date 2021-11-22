;;; merciyes18    27.10.2019
;;;
;;; Comp200 Project 1
;;;
;;; Due Oct 28, 2019
;;;
;;; Before you start:
;;;
;;; * Please read the detailed instructions for this project from the
;;; file project1.pdf available in the Projects section of the
;;; course website.
;;;
;;; * Please read "Project Submission Instructions" carefully and make
;;; sure you understand everything before you start working on your
;;; project in order to avoid problems.
;;;
;;; While you are working:
;;; * Type all your work and notes in the appropriate sections of this file.
;;; * Please do not delete any of the existing lines.
;;; * Use the procedure names given in the instructions.
;;; * Remember to frequently save (C-x C-s) and check-in (comp101-put) your file.
;;; * Use semicolon (;) to comment out text, tests and unused code.
;;; * Remember to document your code.
;;; * Remember our collaboration policy: you can discuss with your friends but
;;;
;;;   *** NOTHING WRITTEN GETS EXCHANGED ***
;;;
;;; When you are done:
;;; * Perform a final save and check-in.
;;; * Please do not make any modifications after midnight on the due date.
;;; * Please send an email comp101help@ku.edu.tr if you have any questions.
;;; * Make sure your file loads without errors.
;;;
;;;   *** IF (load "project1.scm") GIVES ERRORS YOUR PROJECT WILL NOT BE GRADED ***
;;;

;;; Required package implementation and language definition

(#%require (only racket/base random))

;_BANNER_
;;; Problem 1: Some Simple Probability Theory

; Description for factorial: (before the definition of each procedure,
; please write a description about what the procedure does and what
; its input and output should be, making sure the lines are commented
; out with semi-colons)


(define your-answer-here 0)
(define factorial
  (lambda (n)
    (cond  ((= n 0) 1)
           ((< n 0) "This operation is undefined for negative values")
           (else (* n (factorial (- n 1)))))
  ))
; Test cases for factorial: (after the definition of each procedure,
; please cut and paste some test cases you have run, making sure the
; lines are commented out with semi-colons)

(factorial 5 )  ; -> 120
(factorial 7 )  ; -> 5040
(factorial 10 )  ; -> 3628800
(factorial 0 ) ; -> 1
(factorial -1) ; -> "This operation is undefined for negative values"

;;; Description for binomial:

(define binomial
  (lambda (n b) (/ (factorial n) (* (factorial (- n b)) (factorial b))))
)

; Test cases for binomial:
(newline)
(binomial 5 1)  ; -> 5
(binomial 5 2)  ; -> 10
(binomial 10 5)  ; -> 252
(binomial 5 0) ; -> 1

;;; Description for binomial-2:


(define binomial-2
  (lambda (n b)
    (if (= b 0)
        1 (* (/ n b) (binomial-2 (- n 1) (- b 1)))
    )))

; Test cases for binomial-2:
(newline)
(binomial-2 5 1)  ; -> 5
(binomial-2 5 2)  ; -> 10
(binomial-2 10 5)  ; -> 252
(binomial-2 5 0) ; -> 1

;;; Description for exactly-b-smarties:

;;; Helper method
(define pow (lambda (a b) (if (= b 0)
                              1
                              (* a (pow a (- b 1))))))

;Test cases for pow:
(newline)
(pow 4 3) ; -> 64
(pow 0 2) ; -> 0
(pow 2 0) ; -> 1
;;; Pow is defined only for computing positive powers

(define exactly-b-smarties 
  (lambda (n b p)
    (if (< n b)
        0
        (* (* (binomial-2 n b) (pow p b)) (pow (- 1 p) (- n b) ))
        )
))

; Test cases for exactly-b-smarties:
(newline)
(exactly-b-smarties 1 1 0.5)   ; -> 0.5
(exactly-b-smarties 2 1 0.5)   ; -> 0.5
(exactly-b-smarties 2 2 0.5)   ; -> 0.25
(exactly-b-smarties 2 1 0.3)   ; -> 0.42
(exactly-b-smarties 10 2 0.3)  ; -> 0.2334744
(exactly-b-smarties 20 10 0.5) ; -> 0.176197
(exactly-b-smarties 0 2 1)     ; -> 0

;_BANNER_
;;; Problem 2: More Probability Theory

;;; Description for atleast-b-smarties:
;;; Recursive


(define atleast-b-smarties 
  (lambda (n b p)
	(if (< n b) 
		0 
		(+ (exactly-b-smarties n b p) (atleast-b-smarties n (+ b 1) p)))

))

; Test cases for atleast-b-smarties:
(newline)
(atleast-b-smarties 9 5 0.5)        ; -> 0.5
(atleast-b-smarties 19 10 0.5)      ; -> 0.5
(atleast-b-smarties 10 5 0.6)       ; -> 0.8337
(atleast-b-smarties 15 5 0.3)       ; -> 0.4845
(atleast-b-smarties 4 9 0.8)        ; -> 0

;;; Description for atleast-b-smarties-2:
;;; Iterative
(define atleast-b-smarties-2
  (lambda (n b p)
    (atleast-b-smarties-helper 0 n b p)))

(define atleast-b-smarties-helper
  (lambda (probability n b p)
    (if (> b n)
        probability
        (atleast-b-smarties-helper (+ probability (exactly-b-smarties n b p)) n (+ 1 b) p))
))

; Test cases for atleast-b-smarties-2:
(newline)
(atleast-b-smarties-2 9 5 0.5)        ; -> 0.5
(atleast-b-smarties-2 19 10 0.5)      ; -> 0.5
(atleast-b-smarties-2 10 5 0.6)       ; -> 0.8337
(atleast-b-smarties-2 15 5 0.3)       ; -> 0.4845
(atleast-b-smarties 4 9 0.8)          ; -> 0


;_BANNER_
;;; Problem 3: Choosing a Bag

;;; Description for good-bag:

(define good-bag
  (lambda (n p)
    (cond ((< n 8) #f)
          ((> (atleast-b-smarties-2 n 8 p) 0.5) #t)
          (else #f))
))

; Test cases for good-bag:
(newline)
(good-bag 7 1)       ; -> #f
(good-bag 8 1)      ; -> #t
(good-bag 8 0.5)    ; -> #f
(good-bag 8 0.99)   ; -> #t
(good-bag 8 0.8) ; -> #f
(good-bag 16 0.5)    ; -> #t
(good-bag 16 0.7)    ; -> #t
(good-bag 16 0.4)    ; -> #f


;_BANNER_
;;; Problem 4: Choosing a Value for p

;;; Description of minimum-p

(define minimum-p
  (lambda (n inc)
    (minimum-p-helper 0 n inc)
))

(define minimum-p-helper
  (lambda (min-p n inc)
    (cond ((good-bag n min-p) min-p) ((> min-p 1) "This bag cannot be a good bag with any p")
        (else (minimum-p-helper (+ min-p inc) n inc)))
))

; Test cases for minimum-p:
(newline)
(minimum-p 12 0.1)    ; -> 0.7
(minimum-p 12 0.01)    ; -> 0.63
(minimum-p 12 0.001)    ; -> 0.622
(minimum-p 12 0.0001)    ; -> 0.621499
(minimum-p 12 0.00001)    ; ->0.621479

;_BANNER_
;;; Problem 5: Choosing p More Efficiently

;;; Description of minimum-p-new

(define minimum-p-new
  (lambda (n inc)
    
    (minimum-p-new-helper 0 n inc 0)
))

(define minimum-p-new-helper
  (lambda (min-p n inc call)
    (cond ((good-bag n min-p) (display "Number of calls to good-bag: ")
                              (display call)
                              (newline)
                              (display min-p)
                              (newline))
          ((> min-p 1) "This bag cannot be a good bag with any p")
          (else (minimum-p-new-helper (+ min-p inc) n inc (+ 1 call))))
))

; Test cases for minimum-p-new:

(newline)
(minimum-p-new 15 0.1)    ; -> Number of calls to good-bag: 6
                          ;    0.6
(minimum-p-new 15 0.01)   ; -> Number of calls to good-bag: 50
                          ;    0.5000000000000002
(minimum-p-new 15 0.001)  ; -> Number of calls to good-bag: 500
                          ;    0.5000000000000003
(minimum-p-new 15 0.0001) ; -> Number of calls to good-bag: 5001
                          ;    0.5000999999999612
(minimum-p-new 15 0.00001); -> Number of calls to good-bag: 50000
                          ;    0.5000000000003593
(minimum-p-new 8 0.1)     ; -> Number of calls to good-bag: 10
                          ;    0.9999999999999999

;;; Description of minimum-p-binary:

(define minimum-p-binary
  (lambda (n inc)
     (minimum-p-binary-helper n inc 0 1 0))
  )


(define minimum-p-binary-helper
  (lambda (n inc a b count)
    (cond ((< (- b a) inc)
           (display "Number of calls to good-bag: ")
           (display count)
           (newline)
           (display b)
           (newline))
        ((good-bag n  (/ (+ a b) 2.0)) (minimum-p-binary-helper n inc a (/ (+ a b) 2.0) (+ 1 count)))
        (else (minimum-p-binary-helper n inc (/ (+ a b) 2.0) b (+ 1 count))))
))


; Test cases for minimum-p-binary:
(newline)
(minimum-p-binary 12 0.1)    ; -> Number of calls to good-bag: 4
                             ;    5/8
(minimum-p-binary 12 0.01)   ; -> Number of calls to good-bag: 7
                             ;    5/8
(minimum-p-binary 12 0.001)  ; -> Number of calls to good-bag: 10
                             ;    637/1024
(minimum-p-binary 12 0.0001) ; -> Number of calls to good-bag: 14
                             ;    10183/16384
(minimum-p-binary 12 0.00001); -> Number of calls to good-bag: 17
                             ;    40729/65536


;_BANNER_
;;; Problem 6: Monte-Carlo Simulations

;;; Description of coin-toss:

(define coin-toss
  (lambda (p)
    (if (<= (random) p)
        #t
        #f)
    ))

; Test cases for coin-toss:
(newline)
(coin-toss 1)     ; -> #t
(coin-toss 0)     ; -> #f
(coin-toss 0.8)   ; -> #t-#t-#f

;;; Description of random-bag:

(define random-bag
  (lambda (n p)
    (if (= n 0)
        0
        (+ (if (coin-toss p) 1 0) (random-bag (- n 1) p))
    )
))

; Test cases for random-bag:
(newline)
(random-bag 10 1.0)     ; -> 10
(random-bag 20 1.0)     ; -> 20
(random-bag 100 0.5)     ; -> 52
(random-bag 100 0.0)     ; -> 0 
(random-bag 10 0.0)     ; -> 0 
(random-bag 100 0.0001)     ; -> 0

;;; Description of get-m-bags:

(define get-m-bags
  (lambda (m n p)
    (cond ((= m 0) #f)
          ((>= (random-bag n p) 8) #t)
          (else (get-m-bags (- m 1) n p)))
))

; Test cases for get-m-bags:
(newline)
(get-m-bags 1 10 1)      ; -> #t
(get-m-bags 2 20 1)      ; -> #t
(get-m-bags 0 10 1)      ; -> #f
(get-m-bags 100 10 0.0001)      ; -> #f
(get-m-bags 100 4 0.25)      ; -> #f

;;; Description of estimate-good-probability:

(define estimate-good-probability
  (lambda (m n p t)
    (estimate-good-probability-helper m n p t 0 0)
))

(define estimate-good-probability-helper
  (lambda (m n p t true-count count)
    (if (>= count t)
        (/ true-count t)
        (estimate-good-probability-helper m n p t (+ (if (get-m-bags m n p) 1 0) true-count) (+ count 1)))
))
; Test cases for estimate-good-probability:
;(trace estimate-good-probability-helper)
(newline)
(estimate-good-probability 24 12 0.5 1000)    ; -> 124/125 - 199/200 - 997/1000
(estimate-good-probability 24 16 0.5 1000)    ; -> 1 - 1 - 1
(estimate-good-probability 24 16 0.3 1000)    ; -> 21/25 - 169/200 - 169/200
(estimate-good-probability 24 16 0.2 1000)    ; -> 7/50 - 161/1000 - 3/20



;_BANNER_
;;; Problem 7: Monte-Carlo Again

;New definition of the "good-bag"
;o is the number of orange smarties in the current bag
(define good-bag-new
  (lambda (n q o)
    (and (> o (/ n 2)) (< (random-bag (- n o) q) (/ n 5)))
))

;;; Test cases for good-bag-new
(newline)
(good-bag-new 10 0.2 6) ; -> #t
(good-bag-new 15 0.3 5) ; -> #f
(good-bag-new 20 0.4 11); -> #t

;this new get-m-bags-new first chooses the orange smarties with the given probability of p than
;pass it to the good-bag-new function to the jobs.
(define get-m-bags-new
  (lambda (m n p q)
    (cond ((= m 0) #f)
          ((good-bag-new n q (random-bag n p)) #t)
          (else (get-m-bags-new (- m 1) n p q)))
))

;;; Description of estimate-good-probability-2:


(define estimate-good-probability-2
  (lambda (m n p q t)
    (if (or (> (+ p q) 1) (< p 0) (< q 0)) "Invalid inputs"
        (estimate-good-probability-2-helper m n p q t 0 0))
))

(define estimate-good-probability-2-helper
  (lambda (m n p q t true-count count)
    (if (>= count t)
        (/ true-count t)
        (estimate-good-probability-2-helper m n p q t (+ (if (get-m-bags-new m n p q) 1 0) true-count) (+ count 1)))
    
))

;;;Test cases for estimate-good-probability-2:
(newline)
;(trace estimate-good-probability-2-helper)
(estimate-good-probability-2 24 12 0.5 0.9 1000) ; -> "Invalid inputs"
(estimate-good-probability-2 24 16 0.2 0.1 1000) ; -> 29/1000 - 39/1000 - 3/100
(estimate-good-probability-2 1 8 0.5 0.2 1000) ; -> 337/1000 - 79/250 - 7/20

;;;____PLACE FOR ESSAY QUESTIONS_________;
;;;Answer following questions:

;;;1. Why do we test even simple procedures?
;;; Every procedure has edge conditions or values which needs separate handling or better design to account for.
;;; Even the simplest procedures may have them, and testing these procedures separately makes it easier to detect
;;; bugs and maintain our code

;;;2. What makes a test case set a good test case set?
;;; A good test case set is the one that covers as much possible edge conditions it can, also that focuses on the
;;; tests that impact the system more. Good sets constitute of test cases that are similar to real uses. Also, sets
;;; should cover all the paths, the procedure can follow in execution.

;;;3. How can we automate testing?
;;; We can write test scripts that include test case sets and use higher order procedures to take a procedure and test
;;; test case as input and returns if the output is between expected values.These test scripts can run every time the
;;; procedures change.
;;;
;;;4. Why testing is required? Why do we have to test even the simplest procedure?
;;; Testing every procedure itself is called unit testing. It is necessary for several reasons:
;;; * to catch errors better when maintaining or changing code
;;; * to make code more reusable, when every procedure is tested separately, code needs to be modular
;;; * Debugging is easier when we test

;;;5. Testing procedures which use random variables is a problem. How would you test the coin-toss procedure and decide if it is working correctly?
;;; Procedures which contain random variables can be tested as follows:
;;; * Test paths of execution that do not include random variable and verify the behaviour
;;; * For the parts with random variables, execute same test set multiple times to see if the outputs are consistent
;;; and verifying the implemented behaviour.

;;;______________________________________;


;_BANNER_
; END OF PROJECT
; Ignore the following
; This is necessary so the file loads without errors initially:
(define your-code-here #f)

