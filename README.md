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

### Code organization

#### Backend

The backend is organized as follows:

Each responsability, _i.e._ `entities`, `repositories`, `services`, `controllers` has its own package.
Each reponsible package is divided as follow:
  - `application`: contains classes related to job and training applications.
  - `user`: contains classes related to the users and their information.
If any class is direclty in the responsible package, it means that there is no sub-package that corresponds to the class.
All `services` must be declared as an interface in the package `fr.uca.cdr.skillful_network.services`.
All implementations of `services` must be in the sub-package `impl`, _i.e._ namely `fr.uca.cdr.skillful_network.services.impl`.

We adopt the following basic rules:
 - The link between `controllers` and `services` is one-to-one, _i.e._ a `controller` use an unique `service`.
 - `controllers` must not access directly to `repositories` and must use `services` instead.
 - All the business logic must be in `services`.
 - All the network logic must be in the `controllers`.
 - The link between `services` and `repositories` is one-to-one, _i.e._ a `service` use an unique `repository`.

Others packages are the following:
 - `request`: contains class definitions of objects send by the frontend.
 - `security`: contains the classes related to the security configuration.
 - `tools`: contains helper classes.

### API

The API is available at the following url: `http://localhost:8080/swagger-ui.html` while the application backend is running.

### H2 Database

The database for development used in `H2`, that is loaded in the memory. This database is initialized using the `json` files in `skillful_network_server/main/resources/data/`.

While the application backend is running, the database is available at the following url: `localhost:8080/h2`.
The username is `sa` and the password is empty ``.
