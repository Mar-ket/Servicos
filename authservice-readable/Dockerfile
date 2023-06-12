FROM python:3.7-alpine

RUN pip install --upgrade pip

WORKDIR /app_cont
COPY . /app_cont

RUN pip --no-cache-dir install -r requirements.txt

EXPOSE 5000
ENV REDIS_CN_STRING='redis://:@redis-db-service:6379/0'
ENV PARSE='http://parse-service:1337'

CMD ["python3", "app.py"]
