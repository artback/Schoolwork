#!/usr/bin/env bash
[ -e ./worklist.txt ] && rm worklist.txt
./script1.bash www.mechani.se  /unix/public/cw4a.html  
cat worklist.txt | grep "html$" | while read next; do
  domain=$(awk '{print $3}' <<< $next)
	path=$(awk '{print $4}' <<< $next)
  ./script1.bash $domain $path 
done
mv worklist.txt result
exit
