These commands build the Docker image for the receipt processor using docker build and then run the 
container with port 8080 exposed using docker run.

docker build -t receipt-processor .

docker run -p 8080:8080 receipt-processor