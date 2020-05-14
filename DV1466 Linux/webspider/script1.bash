
domainArg=$1 pathArg=$2
wwwpath=$(echo $pathArg | sed 's/\(\/.*\/\).*/\1/g')
result=$(curl -s $domainArg$pathArg |  grep -Po 'href="\K.*?(?=")' | sed -e 's/?.*//g' | grep -v 'css\|mailto' | sed -r "s,(^([a-z].*)),\1,g" )
if [ ! -e "./worklist.txt" ] ; then
    touch "./worklist.txt"
fi
echo $result | tr " " "\n" | while read line ; do 
  temppath=$wwwpath
  [[ "$temppath" == */ ]] && temppath=${temppath::-1}
  while [ $( echo $line | grep "\.\.\/" ) ] ; do 
    temppath="${temppath%\/*}"
    line="${line#../*}"
  done
  http="http"
  if [ "${line/$http}" =  "$line" ]; then
    [[ "${line/$wwwpath}" =  "$line" ]] && line=$temppath'/'$line
    if [ "$(echo $line | head -c 1)" != "/"  ]; then
      line="/"$line
    fi  
    linne=$(echo "$domainArg $pathArg $domainArg $line" | sed -e 's#=.*##')
    if ! grep -qF "$linne" worklist.txt; then 
      echo $linne | tee -a worklist.txt
      fi
  else
    linne=$(echo "$domainArg $pathArg ${line#*//}" | sed -e 's#=.*##' )
    if ! grep -qF "$linne" worklist.txt  ; then 
      echo $linne | tee -a worklist.txt
      fi
  fi 
done 
exit
