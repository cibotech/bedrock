# cibo bedrock

scala-js and scalajs-react based user interface component library.

Currently in-dev, use at your own risk, apis subject to change. 

# Developing
1. Run developer install script (install npm, etc)
2. run `npm install` in project directory
3. run `sbt fastOptJS` to compile the scalajs code.
4. run `gulp develop` to run build and start a development server at `localhost:8000`

# Using
1. Find the latest published version 
2. Add the scala dependency to your `build.sbt`:
```scala
 "io.github.cibotech" %%% "bedrock" % "{bedrockVersion}"
```
3. Include the stylesheet in your HTML header:
```html
 <link rel="stylesheet" href="https://cibotech.github.io/bedrock/resources/css/default.css" />
```

# Plot Artifact

The artifact `bedrock-plots` provides a React component that wraps and displays an EvilPlot `Plot` object.
