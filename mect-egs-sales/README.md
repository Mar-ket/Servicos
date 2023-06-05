
# Mar Ket Place - Sales API

Welcome to Mar Ket Place Sales API! This is a Django Rest Framework project for managing sales in a marketplace.  

## Setup

### Virtual environment

First, you need to create a Python virtual environment to isolate your project's dependencies. Run the following command to create a new virtual environment named "venv":

```sh
python -m  venv  venv
```

Activate the virtual environment:

```sh
source  venv/bin/activate
```

### Install dependencies

To install the project's dependencies, run the following command:

```sh
pip install  -r  requirements.txt
```

### PostgreSQL Database

#### 1. Install PostgreSQL:

You can use your distribution's package manager to install PostgreSQL. For example, on Ubuntu, you can run the following command to install PostgreSQL:

```sh
sudo apt-get  install  postgresql  postgresql-contrib
```

#### 2. Create a database and user:

You need to create a PostgreSQL user and database for your Django project. You can do this using the psql command-line tool. Run the following commands to create a new user and database:

```sql
sudo -u postgres psql
CREATE  DATABASE  mydatabase;
CREATE  USER  myuser  WITH  PASSWORD  'mypassword';
GRANT ALL PRIVILEGES ON  DATABASE mydatabase TO myuser;
```

Replace **mydatabase**, **myuser**, and **mypassword** with your own values.

####  3. Install psycopg2:

psycopg2 is a Python library that allows Python to communicate with PostgreSQL. You can install it using pip:

```sh
pip install  psycopg2
```

#### 4. Configure Django to use PostgreSQL:

Open your Django project's settings.py file and find the DATABASES setting. Replace the default sqlite database settings with the following:

```bash
DATABASES =  {
	'default':  {
		'ENGINE':  'django.db.backends.postgresql',
		'NAME':  'mydatabase',
		'USER':  'myuser',
		'PASSWORD':  'mypassword',
		'HOST':  'localhost',
		'PORT':  '',
	}
}
```

Replace *mydatabase*, *myuser*, and *mypassword* with the same values you used in step 2.

### Configuration

Set the following environment variables in a .env file in the root directory of the project:

```sh
SECRET_KEY=your_secret_key_here
DEBUG=True
PAYMENTS_API_URL=http://localhost:8080/payments
PRODUCTS_API_URL=http://localhost:8080/products
```

### Migrations

```sh
python manage.py  migrate
```

### Run the project

```sh
python manage.py  runserver
```

## Note

Please make sure that *PAYMENTS_API_URL* and *PRODUCTS_API_URL* environment variables are set correctly. The current values are just examples and may not work for your environment.