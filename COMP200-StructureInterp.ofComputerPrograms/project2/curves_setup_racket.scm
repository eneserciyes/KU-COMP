;; setup graphics interfaces for curves project
(#%require graphics/graphics)


;; need to load the following
;; curves_utils.scm
;; curves.scm
;; drawing.scm
;;
;; then need to evaluated (setup-windows)
(define g1 #f)
(define g2 #f)
(define g3 #f)

(load "curves_utils.scm")
(load "curves.scm")
(load "drawing_racket.scm")

(define (setup-windows)
  (begin (close-graphics)
         (open-graphics)
         (set! g1 (open-viewport "Graphics: g1" 256 256))
         (set! g2 (open-viewport "Graphics: g2" 256 256))
         (set! g3 (open-viewport "Graphics: g3" 256 256))
         )
  )
