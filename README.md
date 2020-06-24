[![Build Status](https://travis-ci.org/danglotb/skillful_network.svg?branch=master)](https://travis-ci.org/danglotb/skillful_network) [![Coverage Status](https://coveralls.io/repos/github/danglotb/skillful_network/badge.svg?branch=master)](https://coveralls.io/github/danglotb/skillful_network?branch=master)

# Skillful Network

## Run

Clone the projet:
```sh
git clone https://github.com/danglotb/skillful_network.git
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

You can login using `user@uca.fr` and `Qwerty123`, or create a new account (the temporary code will appear in the console of the backend).

### Version Control Service (VCS)

#### Pull-requets based development

We use pull-request based development. That is to say, no one is allowed to push direclty to the master, but must use the pull-request feature of GitHub.
The pull-request must be as small as possible, reduced to the smallest changes possible.
You cannot merge your own pull-request, and to merge the pull-request of someone, the continuous integration must pass and must go through each changes ensuring at least the best practices (described in the next section) are respected.
When a pull-request is merged, please, remove it from the repository.

#### Branches and commits nomenclature

We use the following convention. For branches:

```
number-initials-type-what
```

with `number`, the task number (can be subtask), `initials` your initials, `type` the type of the changes, `what` an abstract words refering to the what your branch is achieving.

For example, if I create a branch for the task 0, that is a documentation branch about VCS, I'll create the following branch:
```
0-bd-doc-vcs
```

For commits:

```
number(initials) type: message
```

with `number`, the task number (can be subtask), `initials` your initials, `type` the type of the changes, `message` the message describing the commit.

For example, in the same context, a commit message would be:

```
0(bd) doc: add Version Control System section with nomenclature for branches and commits
```

#### Project, Issues, Status and Pull-requests

If the pull request is fixing a issue, you can add in one of the commit message a reference to the issue: `FIX #number`. This will be added at the end of the commit message.

For example, I can add to the previous commit message `FIX #20` to link it to the issue as follow:
```
0(bd) doc: add Version Control System section with nomenclature for branches and commits FIX #20
```

### Code organization

#### Frontend

The backend is organized as follows:

  - `app`: root of the project
    - `login`: contains everything related to the first screen: login and register.
    - `shared`: contains common and shared apis.
      - `components`: contains abstract and custom material components. Should be reused a maximum.
      - `models`: contains classes to represent the data manipulated by the application.
      - `services`: contains all the `services` to requests the backend
    - `home`: contains everythin concerning the rest of the platform.

We must reuse a maximum the abstract `components` in order to avoid redundancy of code. Create a new one if you need to.
Every requests made to the backend should be handled by the `shared.services.ApiHelperService`, _i.e._ calls to the API `http: HttpClient`.
However, every `components` must use a unique and dedicated `service` to make these requests, and not direclty the `shared.services.ApiHelperService`.
The relation between frontend `services` and backend `controllers` must be one-to-one, _i.e._ each frontend `service` requests a unique backend `controller`.
But, a `service` can use others `services` if it needs to.

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

The json files are generated using the test class `fr.uca.cdr.skillful_network.JSONGenerator`. If you need special instances of entities, you should update the test with you required datas by constructing new objects (please, respect the separation of concerns, each entity has its own method to be generated), generate the json file, and commit your change in the `JSONGenerator` and in json files.
This is done in order to share and populate the development database.
