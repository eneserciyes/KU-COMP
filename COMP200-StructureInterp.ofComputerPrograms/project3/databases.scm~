;;;;
; tables.scm - Fall 2019
; 
; this project deals with some simple relational databases, using 
; abstract data types
;;;;

;;;;;;;;;;;;;;;;;;;;;
;;;; some basics ;;;;
;;;;;;;;;;;;;;;;;;;;;

; filter implementation R5RS does not include it!
(define (filter f items)
  (cond
    ((null? items) '())
    ((f (car items))
     (cons (car items) (filter f (cdr items))))
    (else (filter f (cdr items)))))

; error
(define (error reason . args)
      (display "Error: ")
      (display reason)
      (for-each (lambda (arg) 
                  (display " ")
		  (write arg))
		args)
      (newline)
      (scheme-report-environment 5))
; compose
(define (compose f g)
  (lambda (x)
    (f (g x))))

; tagged data
;
(define (tag-check object tag)
  (and (pair? object)
       (eq? tag (car object))))

(define (tag object)
  (if (pair? object)
      (car object)
      (error "object not tagged data")))

(define (contents object)
  (if (pair? object)
      (cdr object)
      (error "object not tagged data")))

;;;;;;;;;;;;;;;;;;;;
;;;; type-table ;;;;
;;;;;;;;;;;;;;;;;;;;

; definition - association list of
;  (name checker comparator)
;
; checker:  A->boolean
;  returns #t if type of A matches type
; comparator:  T,T->boolean
;  returns #t if first arg is "less than" second arg
;

(define (symbol<? s1 s2)
  (string<? (symbol->string s1) (symbol->string s2)))

(define *type-table*
  (list 
        (list 'number number? <)
        (list 'symbol symbol? symbol<?)))

(define (type-checker type)
  ; looks up the checker for the given type
  ; in the type-table
  (let ((r (assq type *type-table*)))
    (if r
	(cadr r)
	(error "unknown type" type))))

(define (type-comparator type)
  ; looks up the comparator for the given type
  ; in the type-table
  (let ((r (assq type *type-table*)))
    (if r
	(caddr r)
	(error "unknown type" type))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;; column abstraction ;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(define (make-column name type)
  ; constructor:  name,type -> column
  (list name type))

(define (column-name col)
  ; selector: column->name
  (car col))

(define (column-type col)
  ; selector: column->type
  (cadr col))

;;;;;;;;;;;;;;;;;;;;;;;;;
;;;; row abstraction ;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;

; tag definition
(define *row-tag* 'row)

(define (make-row cols row-data)
  ; constructor: list<column>,list -> row
  (cons *row-tag*
	(map (lambda (col datum) ;note: uses built-in map
	       (cons col datum)) ; which can take two lists
	     cols
	     row-data)))

(define (row? x)
  ; predicate:  A->boolean
  (tag-check x *row-tag*)) ; correction - was tag-check?

(define (row-columns row)
  ; selector:  row -> list<columns>
  (map car (contents row)))

(define (row-data row)
  ; selector: row -> list
  (map cdr (contents row)))


(define (rlookup colname row-contents)
  ; internal procedure for looking up the item in the
  ; row based on the column name
  (let ((data (filter (lambda (x) (eq? colname (column-name (car x))))
                      row-contents)))
    (if (null? data)
        #f
        (car data))))
   
  
(define (get colname row)
  ; selector: column-name,row -> value
  ; looks up a value in row based on column-name
  (let ((result (rlookup colname (contents row))))
    (if result
	(cdr result)
	(error "Bad column name in get"))))

(define (row-col-replace row colname newvalue)
  ; update:  row,column-name,value -> row
  ; returns a new row with the value in column replaced with newvalue
  ; doesn't verify that the new data matches the type
  (define (helper row-data)
    (cond ((null? row-data)
	   (error "unknown col in row-col-replace"))
	  ((eq? colname (column-name (caar row-data)))
	   (cons (cons (caar row-data) newvalue) (cdr row-data)))
	  (else
	   (cons (car row-data)
		 (helper (cdr row-data))))))
  (cons *row-tag* (helper (contents row))))

(define (row-display row)
  ; prints out values of row
  (for-each (lambda (val)
	      (display val) (display "\t"))
	    (row-data row)))

(define (row-type-check row)
  ; row->boolean
  ; verifies that the data in the row matches the types
  ; specified for the columns
  (define (check-helper cols data)
    (if (null? cols)
	#t
	(let ((col (car cols))
	      (datum (car data)))
	  (if ((type-checker (column-type col))  datum)
	      (check-helper (cdr cols) (cdr data))
	      #f))))
  (check-helper (row-columns row) (row-data row)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;; table abstraction ;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;

; tag definition
(define *table-tag* 'table)

(define (table? x)
  ; predicate
  (tag-check x *table-tag*))

(define (make-empty-table cols)
  ; list<column> -> table
  (make-table cols '()))

(define (make-table cols data)
  ; internal proc - list<column>,list<row> -> table
  (list *table-tag* cols data))

(define (get-table-columns table)
  ; selector: table -> list<column>
  (cadr table))

(define (get-table-data table)
  ; selector: table -> list<row>
  (caddr table))

(define (change-table-data! table newdata)
  ; selector: table,list<row> -> table
  ; actually modifies table, ignore details of how
  (set-car! (cddr table) newdata)
  table)

(define (empty-table? table)
  ; returns true if the table is empty
  (null? (get-table-data table)))

(define (table-num-rows table)
  ; returns number of rows in table
  (length (get-table-data table)))

(define (table-nth-row n table)
  ; extracts nth row from the table
  (list-ref (get-table-data table) n))

(define (table-map proc table)
  ; maps proc over the rows of the table
  (map proc (get-table-data table)))

(define (fold-right proc init lst)
  (if (null? lst)
      init
      (proc (car lst) (fold-right proc init (cdr lst)))))

(define (table-fold-right proc init table)
  ; fold-right of proc,init over the table
  (fold-right proc init (get-table-data table)))

(define (table-display table)
  ; displays a table
  ;  displays column names, then row by row
  (for-each (lambda (col)
	      (display (column-name col)) (display "\t"))
	    (get-table-columns table))
  (newline)
  (for-each (lambda (row)
	      (row-display row) (newline))
	    (get-table-data table)))

(define (make-row-comparator colname table)
  ; given a column name and a table, finds an appropriate
  ; comparator for the type of the column
  (let* ((cols (get-table-columns table))
	 (col (assq colname cols))
	 (comp (type-comparator (column-type col))))
    (lambda (r1 r2)
      (comp (get colname r1)
	    (get colname r2)))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;; table manipulation procs ;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(define (table-insert! row-data table)
  ; list,table -> table
  ; modifies & returns the table to include the new row
  ; assuming that the row-data passes the type-check
  (let ((new-row (make-row (get-table-columns table)
                           row-data)))
    (if (row-type-check new-row)
        (change-table-data!
         table
         (cons new-row (get-table-data table)))
        (error "type check failed in insert" row-data))))
;;;
;some useful procedures
;;;

(define (find-best pred best rest)
  (if (null? rest)
      best
      (if (pred best (car rest))
          (find-best pred best (cdr rest))
          (find-best pred (car rest) (cdr rest)))))

(define (remove elt lst)
  (if (null? lst)
      '()
      (if (eq? elt (car lst))
          (remove elt (cdr lst))
          (cons (car lst) (remove elt (cdr lst))))))

(define (sort predicate lst)
  (if (null? lst)
      '()
      (let ((next (find-best predicate (car lst) (cdr lst))))
        (let ((rest (remove next lst)))
          (cons next (sort predicate rest))))))
  

;;; below is the sample database

;columns are:
;year
;all-all
;all-male
;all-female
;white-all
;white-male
;white-female
;black-all
;black-male
;black-female

(define life-expect-data
'(
(2016 78.7 76.2 81.1 78.9 76.4 81.3 75.3 72.0 78.3)
(2015 78.7 76.3 81.1 78.9 76.6 81.3 75.5 72.2 78.5)
(2014 78.9 76.5 81.3 79.1 76.7 81.4 75.6 72.5 78.5)
(2013 78.8 76.4 81.2 79.0 76.7 81.4 75.5 72.3 78.4)
(2012 78.8 76.4 81.2 79.1 76.7 81.4 75.5 72.3 78.4)
(2011 78.7 76.3 81.1 79.0 76.6 81.3 75.3 72.2 78.2)
(2010 78.7 76.2 81.0 78.9 76.5 81.3 75.1 71.8 78.0)
(2009 78.5 76.0 80.9 78.8 76.4 81.2 74.7 71.4 77.7)
(2008 78.2 75.6 80.6 78.5 76.1 80.9 74.3 70.9 77.3)
(2007 78.1 75.5 80.6 78.5 76.0 80.9 73.8 70.3 77.0)
(2006 77.8 75.2 80.3 78.3 75.8 80.7 73.4 69.9 76.7)
(2005 77.6 75.0 80.1 78.0 75.5 80.5 73.0 69.5 76.2)
(2004 77.6 75.0 80.1 78.1 75.5 80.5 72.9 69.4 76.1)
(2003 77.2 74.5 79.7 77.7 75.1 80.2 72.4 68.9 75.7)
(2002 77.0 74.4 79.6 77.5 74.9 80.1 72.2 68.7 75.4)
(2001 77.0 74.3 79.5 77.5 74.9 80.0 72.0 68.5 75.3)
(2001 77.2 74.4 79.8 77.7 75.0 80.2 72.2 68.6 75.5)
(2000 77.0 74.3 79.7 77.6 74.9 80.1 71.9 68.3 75.2)
(1999 76.7 73.9 79.4 77.3 74.6 79.9 71.4 67.8 74.7)
(1998 76.7 73.8 79.5 77.3 74.5 80.0 71.3 67.6 74.8)
(1997 76.5 73.6 79.4 77.2 74.3 79.9 71.1 67.2 74.7)
(1996 76.1 73.1 79.1 76.8 73.9 79.7 70.2 66.1 74.2)
(1995 75.8 72.5 78.9 76.5 73.4 79.6 69.6 65.2 73.9)
(1994 75.7 72.4 79.0 76.5 73.3 79.6 69.5 64.9 73.9)
(1993 75.5 72.2 78.8 76.3 73.1 79.5 69.2 64.6 73.7)
(1992 75.8 72.3 79.1 76.5 73.2 79.8 69.6 65.0 73.9)
(1991 75.5 72.0 78.9 76.3 72.9 79.6 69.3 64.6 73.8)
(1990 75.4 71.8 78.8 76.1 72.7 79.4 69.1 64.5 73.6)
(1989 75.1 71.7 78.5 75.9 72.5 79.2 68.8 64.3 73.3)
(1988 74.9 71.4 78.3 75.6 72.2 78.9 68.9 64.4 73.2)
(1987 74.9 71.4 78.3 75.6 72.1 78.9 69.1 64.7 73.4)
(1986 74.7 71.2 78.2 75.4 71.9 78.8 69.1 64.8 73.4)
(1985 74.7 71.1 78.2 75.3 71.8 78.7 69.3 65.0 73.4)
(1984 74.7 71.1 78.2 75.3 71.8 78.7 69.5 65.3 73.6)
(1983 74.6 71.0 78.1 75.2 71.6 78.7 69.4 65.2 73.5)
(1982 74.5 70.8 78.1 75.1 71.5 78.7 69.4 65.1 73.6)
(1981 74.1 70.4 77.8 74.8 71.1 78.4 68.9 64.5 73.2)
(1980 73.7 70.0 77.4 74.4 70.7 78.1 68.1 63.8 72.5)
(1979 73.9 70.0 77.8 74.6 70.8 78.4 68.5 64.0 72.9)
(1978 73.5 69.6 77.3 74.1 70.4 78.0 68.1 63.7 72.4)
(1977 73.3 69.5 77.2 74.0 70.2 77.9 67.7 63.4 72.0)
(1976 72.9 69.1 76.8 73.6 69.9 77.5 67.2 62.9 71.6)
(1975 72.6 68.8 76.6 73.4 69.5 77.3 66.8 62.4 71.3)
(1974 72.0 68.2 75.9 72.8 69.0 76.7 66.0 61.7 70.3)
(1973 71.4 67.6 75.3 72.2 68.5 76.1 65.0 60.9 69.3)
(1972 71.2 67.4 75.1 72.0 68.3 75.9 64.7 60.4 69.1)
(1971 71.1 67.4 75.0 72.0 68.3 75.8 64.6 60.5 68.9)
(1970 70.8 67.1 74.7 71.7 68.0 75.6 64.1 60.0 68.3)
(1969 70.5 66.8 74.4 71.4 67.7 75.3 64.5 60.6 68.6)
(1968 70.2 66.6 74.1 71.1 67.5 75.0 64.1 60.4 67.9)
(1967 70.5 67.0 74.3 71.4 67.8 75.2 64.9 61.4 68.5)
(1966 70.2 66.7 73.9 71.1 67.5 74.8 64.2 60.9 67.6)
(1965 70.2 66.8 73.8 71.1 67.6 74.8 64.3 61.2 67.6)
(1964 70.2 66.8 73.7 71.0 67.7 74.7 64.2 61.3 67.3)
(1963 69.9 66.6 73.4 70.8 67.4 74.4 63.7 61.0 66.6)
(1962 70.1 66.9 73.5 70.9 67.7 74.5 64.2 61.6 66.9)
(1961 70.2 67.1 73.6 71.0 67.8 74.6 64.5 62.0 67.1)
(1960 69.7 66.6 73.1 70.6 67.4 74.1 63.6 61.1 66.3)
(1959 69.9 66.8 73.2 70.7 67.5 74.2 63.9 61.3 66.5)
(1958 69.6 66.6 72.9 70.5 67.4 73.9 63.4 61.0 65.8)
(1957 69.5 66.4 72.7 70.3 67.2 73.7 63.0 60.7 65.5)
(1956 69.7 66.7 72.9 70.5 67.5 73.9 63.6 61.3 66.1)
(1955 69.6 66.7 72.8 70.5 67.4 73.7 63.7 61.4 66.1)
(1954 69.6 66.7 72.8 70.5 67.5 73.7 63.4 61.1 65.9)
(1953 68.8 66.0 72.0 69.7 66.8 73.0 62.0 59.7 64.5)
(1952 68.6 65.8 71.6 69.5 66.6 72.6 61.4 59.1 63.8)
(1951 68.4 65.6 71.4 69.3 66.5 72.4 61.2 59.2 63.4)
(1950 68.2 65.6 71.1 69.1 66.5 72.2 60.8 59.1 62.9)
(1949 68.0 65.2 70.7 68.8 66.2 71.9 60.6 58.9 62.7)
(1948 67.2 64.6 69.9 68.0 65.5 71.0 60.0 58.1 62.5)
(1947 66.8 64.4 69.7 67.6 65.2 70.5 59.7 57.9 61.9)
(1946 66.7 64.4 69.4 67.5 65.1 70.3 59.1 57.5 61.0)
(1945 65.9 63.6 67.9 66.8 64.4 69.5 57.7 56.1 59.6)
(1944 65.2 63.6 66.8 66.2 64.5 68.4 56.6 55.8 57.7)
(1943 63.3 62.4 64.4 64.2 63.2 65.7 55.6 55.4 56.1)
(1942 66.2 64.7 67.9 67.3 65.9 69.4 56.6 55.4 58.2)
(1941 64.8 63.1 66.8 66.2 64.4 68.5 53.8 52.5 55.3)
(1940 62.9 60.8 65.2 64.2 62.1 66.6 53.1 51.5 54.9)
(1939 63.7 62.1 65.4 64.9 63.3 66.6 54.5 53.2 56.0)
(1938 63.5 61.9 65.3 65.0 63.2 66.8 52.9 51.7 54.3)
(1937 60.0 58.0 62.4 61.4 59.3 63.8 50.3 48.3 52.5)
(1936 58.5 56.6 60.6 59.8 58.0 61.9 49.0 47.0 51.4)
(1935 61.7 59.9 63.9 62.9 61.0 65.0 53.1 51.3 55.2)
(1934 61.1 59.3 63.3 62.4 60.5 64.6 51.8 50.2 53.7)
(1933 63.3 61.7 65.1 64.3 62.7 66.3 54.7 53.5 56.0)
(1932 62.1 61.0 63.5 63.2 62.0 64.5 53.7 52.8 54.6)
(1931 61.1 59.4 63.1 62.6 60.8 64.7 50.4 49.5 51.5)
(1930 59.7 58.1 61.6 61.4 59.7 63.5 48.1 47.3 49.2)
(1929 57.1 55.8 58.7 58.6 57.2 60.3 46.7 45.7 47.8)
(1928 56.8 55.6 58.3 58.4 57.0 60.0 46.3 45.6 47.0)
(1927 60.4 59.0 62.1 62.0 60.5 63.9 48.2 47.6 48.9)
(1926 56.7 55.5 58.0 58.2 57.0 59.6 44.6 43.7 45.6)
(1925 59.0 57.6 60.6 60.7 59.3 62.4 45.7 44.9 46.7)
(1924 59.7 58.1 61.5 61.4 59.8 63.4 46.6 45.5 47.8)
(1923 57.2 56.1 58.5 58.3 57.1 59.6 48.3 47.7 48.9)
(1922 59.6 58.4 61.0 60.4 59.1 61.9 52.4 51.8 53.0)
(1921 60.8 60.0 61.8 61.8 60.8 62.9 51.5 51.6 51.3)
(1920 54.1 53.6 54.6 54.9 54.4 55.6 45.3 45.5 45.2)
(1919 54.7 53.5 56.0 55.8 54.5 57.4 44.5 44.5 44.4)
(1918 39.1 36.6 42.2 39.8 37.1 43.2 31.1 29.9 32.5)
(1917 50.9 48.4 54.0 52.0 49.3 55.3 38.8 37.0 40.8)
(1916 51.7 49.6 54.3 52.5 50.2 55.2 41.3 39.6 43.1)
(1915 54.5 52.5 56.8 55.1 53.1 57.5 38.9 37.5 40.5)
(1914 54.2 52.0 56.8 54.9 52.7 57.5 38.9 37.1 40.8)
(1913 52.5 50.3 55.0 53.0 50.8 55.7 38.4 36.7 40.3)
(1912 53.5 51.5 55.9 53.9 51.9 56.2 37.9 35.9 40.0)
(1911 52.6 50.9 54.4 53.0 51.3 54.9 36.4 34.6 38.2)
(1910 50.0 48.4 51.8 50.3 48.6 52.0 35.6 33.8 37.5)
(1909 52.1 50.5 53.8 52.5 50.9 54.2 35.7 34.2 37.3)
(1908 51.1 49.5 52.8 51.5 49.9 53.3 34.9 33.8 36.0)
(1907 47.6 45.6 49.9 48.1 46.0 50.4 32.5 31.1 34.0)
(1906 48.7 46.9 50.8 49.3 47.3 51.4 32.9 31.8 33.9)
(1905 48.7 47.3 50.2 49.1 47.6 50.6 31.3 29.6 33.1)
(1904 47.6 46.2 49.1 48.0 46.6 49.5 30.8 29.1 32.7)
(1903 50.5 49.1 52.0 50.9 49.5 52.5 33.1 31.7 34.6)
(1902 51.5 49.8 53.4 51.9 50.2 53.8 34.6 32.9 36.4)
(1901 49.1 47.6 50.6 49.4 48.0 51.0 33.7 32.2 35.3)
(1900 47.3 46.3 48.3 47.6 46.6 48.7 33.0 32.5 33.5)
))
