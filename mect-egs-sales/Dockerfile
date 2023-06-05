FROM python:3.10.0-buster
     
WORKDIR /opt/app


# install dependencies
RUN pip install --upgrade pip
COPY ./requirements.txt /opt/app/requirements.txt

RUN chmod +x /opt/app/requirements.txt
RUN pip install -r requirements.txt

# copy project
COPY . /opt/app/
RUN chmod +x /opt/app/docker-entrypoint.sh

RUN ls -alh

ENTRYPOINT [ "/opt/app/docker-entrypoint.sh" ]
