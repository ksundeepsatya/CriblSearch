### How to Run

- Install docker from https://docs.docker.com/engine/install/
- check out the project locally
- cd to root directory of check out 
- Execute Below command
```
docker-compose up 
```
- If everything goes correct you should see this text up on docker start: "All Systems GO!"

### Rest Request
```
http://localhost:8000/search?filePath=<file path>&search=<Case Sensitive String>&n=<[optional] line limit>&fileReadType=<[optional] File Read type>&searchAlgoType=<[optional] Search Algorith type>

Eg: 
http://localhost:8000/search?filePath=install.log&search=DYLD_NO_FIX_PREBINDING

http://localhost:8000/search?filePath=install.log&search=DYLD_NO_FIX_PREBINDING&n=5&fileReadType=RandomAccessReverse&searchAlgoType=BoyerMoore
```
- filePath : file path realtive to /var/log. In above example file /var/log/install.log will be searched
- search : Case Sensitve search string
- n : [optional]
	- number of lines to be searched, if 0 all mathcing lines in the file will be returned
	- Default value = 0
- fileReadType: [optional]
	- RandomAccessReverse :Uses RandomAccessFile.  Implementation src/main/java/com/cribl/file/RandomFileAccessReverseRead.java
	- ApacheReverse: Uses Apache common library. Implementation src/main/java/com/cribl/file/ApacheReverseFileReader.java
	- ForwardRead: Reads from beginning of the file, This is used when all lines are being searched. Implementation src/main/java/com/cribl/file/BufferedForwardRead.java
	- Default : If nothing is specified this is used. Internally uses RandomAccessReverse
- searchAlgoType : [optional]
	- BoyerMoore: Uses Boyer Moore Algorithm.
	- RabinKarp: Uses Rabin Karp Algorithm.
	- JavaContains: Uses Java's String.Contains
	- NoSearch: Doesn't search, used for benchmarking file reads
	- Default: If nothing is specified this is used. Internally uses BoyerMoore

### Notes
- Main consideration for this project is time, there are lot of improvements and features that can be added. But, had to time bound to one day that limits the functionality.
- Impletemted multiple search and file read options. Each one has pros and cons.  Added logging to identify what algorithm works best for different files. We can benchmark them as we do more searches. Then we can build some huristics based on file pattern
- Why docker? Easier control on files that this service has access to, memory and cpu allocation is easy, easy to package and test given I am not sure how familiar the reviwer of the code is with Java environment.
- Not great exception handling

### Performance

File Size 250 MB
In Seconds

|   |  BoyerMoore | RabinKarp  |  JavaContains |  NoSearch |
| ------------ | ------------ | ------------ | ------------ | ------------ |
| RandomAccessReverse  |  22.29 | 25.41  | 23.55  |  57.32 |
| ApacheReverse | 23.97  |  25.79 | 24.9  | 57.24  |
| ForwardRead | NA  | NA  | NA  | 43.27  | |

Should comapre memory usage as well.

