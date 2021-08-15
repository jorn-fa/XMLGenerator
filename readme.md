#XmlGenerator:
<p>
XmlGenerator is a program written to read a specific file format called .i3d which 
is used <br> in farming simulator. The file structure itself is xml based.</p>
<p>The program is designed to read the source file and create 2 files based on 
<br>the contents of the source file.</p>




## Optimal Flow:
<p> User introduces a .i3d file to the program and the program generates these files:
<br> 
<br>ModDesc.xml 
<br>Vehicle.xml
</p>

#Inner workings
<p>
The program cross references entries via configurable json file and outputs xml
<br>based on a predefined xml layout
</p>


##Used techs:
SpringBoot, Lombok, Swing, Slf4j,
<p> Others still to be determined at this point of writing</p>

