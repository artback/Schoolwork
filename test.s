.data
buf:	.space	64
	.text
	.global	main
main:
movq $buf, %rdi
movq $90 , %rsi
movq stdin, %rdx 
call fgets

movq $buf, %rdi
call puts



        cmpq $63, outPos
        jge outImage
        leaq outbuf,%r8
        movq $0, %r9
        addq outPos,%r8
	    cmpq	$'0', %rdi
	    jl	putEND		#teckenkod mindre än '0'
	    cmpq	$100, %rdi
        jl IntLoop2
IntLoop:        
        movb 0(%rdi,%r9,1), %r10b
	    cmpb	$'0', %r10b
	    jl	putEND		#teckenkod mindre än '0'
	    cmpb	$'9', %r10b
	    jg	putEND		#teckenkod större än '9'
	    subb	$'0', %r10b	#subtrahera så talet motsvarande teckenkoden kvarstår
	    movb	%r10b, (%r8) #hämta bokstavens teckenkod
	    incq	%r8		#stega fram till nästa tecken
	    incq	%r9		#stega fram till nästa tecken
        jmp IntLoop
IntLoop2:
