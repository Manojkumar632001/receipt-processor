These commands build the Docker image for the receipt processor using docker build and then run the 
container with port 8080 exposed using docker run.

Step 1: Run docker build -t receipt-processor . to build the image.
Step 2: Run docker run -p 8080:8080 receipt-processor to run the application inside the container.
