# Skillful Network

## Run

Clone the projet:
```sh
git clone https://git.univ-cotedazur.fr/centre-de-reference-defis-du-numerique/skillful-network.git
```

Run the frontend:
```sh
cd skillful_network_client
```

Run the backend
```sh
cd skillful_network_server
```

Check that the application is running by going to the following url:
```sh
http://localhost:4200
```

You can login using `john@uca.fr` and `12345678`, or create a new account (the temporary code will appear in the console of the backend).

### Hierarchy

#### Backend

### API

The API is available at the following url: `http://localhost:8080/swagger-ui.html` while the application backend is running.

### H2 Database

The database for development used in `H2`, that is loaded in the memory. This database is initialized using the `json` files in `skillful_network_server/main/resources/data/`.

While the application backend is running, the database is available at the following url: `localhost:8080/h2`.
The username is `sa` and the password is empty ``.
