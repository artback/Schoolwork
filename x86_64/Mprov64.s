.data
headMsg:	.asciz	"Start av testprogram. Skriv in 5 tal!"
endMsg:	.asciz	"Slut på testprogram"
buf:	.space	64
sum:	.quad	0	
count:	.quad	0	
temp:	.quad	0	

	.text
	
	.global	main
main:
	subq	$8,%rsp
	movq	$headMsg,%rdi
	call	putText
	call	outImage
	call	inImage
	movq	$5,count

UP:	call	getInt
	movq	%rax,temp
	cmpq	$0,%rax
	jge	PP1
	call	getOutPos
	decq	%rax
	movq	%rax,%rdi
	call	setOutPos

PP1:	movq	temp,%rdx
	add	%rdx,sum
	movq	%rdx,%rdi
	call	putInt
	movq	$'+',%rdi
	call	putChar
	decq	count
	cmpq	$0,count
	jne	UP
	call	getOutPos
	decq	%rax
	movq	%rax,%rdi
	call	setOutPos
	movq	$'=',%rdi
	call	putChar
	movq	sum, %rdi
	call	putInt
	call	outImage
	movq	$12,%rsi
	movq	$buf,%rdi
	call	getText
	movq	$buf,%rdi
	call	putText
	movq	$125,%rdi
	call	putInt
	call	outImage
	movq	$endMsg,%rdi
	call	putText
	call	outImage
	addq	$8,%rsp
	call	exit

