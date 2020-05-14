#!/usr/bin/env bash
> graph.dot
cat "./result" | while read next
do
	target=$(echo $next | awk '{print $3,$4}' | sed 's# -##' | sed 's#\(http\|https\)://##')
	source=$(echo $next | awk '{print $1,$2}' | sed 's# -##' | sed 's#\(http\|https\)://##' | sed 's/\(.nsf\).*/\1/')
	echo "	\"$source\" -> \"$target" | sed 's/$/";/' >> graph.dot
done


sed -i '1 i\digraph{' graph.dot
echo '}' >> graph.dot
sed -i '/\" \" -> \" \"/d' graph.dot
exit
