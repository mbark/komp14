LATEX?=xelatex

.PHONY: default pdf distclean clean

default: pdf

pdf: report.pdf

report.pdf: report.tex
	$(LATEX) report
	$(LATEX) report

distclean: clean
	rm -f report.pdf

clean:
	rm -f *.aux *.log *.out
