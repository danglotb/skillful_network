# Extended Documentation

## Version Control Service (VCS)

### Pull-requets based development

We use pull-request based development. That is to say, no one is allowed to push directly to the master branch but must use the pull-request feature of GitHub.
The pull-request must be as small as possible, reduced to the smallest changes possible.
You cannot merge your pull-request, and to merge the pull-request of someone, the continuous integration must pass and must go through each change ensuring at least the best practices (described in the next section) are respected.
When a pull-request is merged, please, remove it from the repository.

### Branches and commits nomenclature

We use the following convention. For branches:

```
number-initials-type-what
```

with `number`, the task number (can be subtask), `initials` your initials, `type` the type of the changes, `what` an abstract words referring to what your branch is achieving.

For example, if I create a branch for task 0, that is a documentation branch about VCS, I'll create the following branch:
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
0(bd) doc: add the Version Control System section with the nomenclature for branches and commits
```

### Project, Issues, Status, and Pull-requests

If the pull request is fixing an issue, you can add in one of the commit message a reference to the issue: `FIX #number`. This will be added at the end of the commit message.

For example, I can add to the previous commit message `FIX #20` to link it to the issue as follow:
```
0(bd) doc: add Version Control System section with the nomenclature for branches and commits FIX #20
```

## Troubleshooting with conflicts and pull-requests

You build your great feature and opened a pull-request. Nobody can review today so you leave it until tomorrow.
The next day, the pull-request cannot be directly merged into master anymore because there have been some changes during the night.

To fix the issue you can apply the following protocol:

1. Add and commit your changes to your local branch (_e.g._ `0-bd-test-unique-json-element`):

```sh
git add <files>
git commit -m "message"
```

If you do not want to commit them, but keep them, you can `git stash` the changes away, and get them back with `git stash pop`.

2. Update your local master branch:

```sh
git checkout master
git pull origin master
```

3. Go back to your branch and rebase it

```sh
git checkout 0-bd-test-unique-json-element
git rebase master
```

What is happening is that git rewrite of your local branch by adding the new commit of the master.

For example, you had two commits on your branch:

```sh
commit 8aba87c4f39e18fd7bc7585672ca038ad9e9cf46
Author: danglotb <bdanglot@gmail.com>
Date:   Wed Jun 24 17:37:57 2020 +0200

    0(bd) test: add assertion that verify we push the new version of json files

commit 6da4c12d97685704bd1a18c8fd16a64f03e14c68
Author: danglotb <bdanglot@gmail.com>
Date:   Wed Jun 24 16:53:40 2020 +0200

    0(bd) test: ensure uniqueness of element in the json
```

However, on the master branch there is a commit that has been pushed at 17:00.

```sh
commit 6da4c12d97685704bd1a18c8fd16a64f03e14c68
Author: someone <some.one@gmail.com>
Date:   Wed Jun 24 17:00:00 2020 +0200

    best commit ever
```

When you rebase, git will basically do this:

```sh
commit 8aba87c4f39e18fd7bc7585672ca038ad9e9cf46
Author: danglotb <bdanglot@gmail.com>
Date:   Wed Jun 24 17:37:57 2020 +0200

    0(bd) test: add assertion that verify we push the new version of json files

commit 6da4c12d97685704bd1a18c8fd16a64f03e14c68
Author: someone <some.one@gmail.com>
Date:   Wed Jun 24 17:00:00 2020 +0200

    best commit ever

commit 6da4c12d97685704bd1a18c8fd16a64f03e14c68
Author: danglotb <bdanglot@gmail.com>
Date:   Wed Jun 24 16:53:40 2020 +0200

    0(bd) test: ensure uniqueness of element in the json
```

When you rebase, you might encounter conflicts (most of the time, this is why we rebase).
Conflicts when rebasing are the same as conflicts while merging.
Just spam the `git status` to know which files are in conflicts, remove the `<<<<` and `>>>>` and mark the conflicts as resolved with: `git add <file>`.

To pursue the rebasing, type: `git rebase --continue`.

At the end of the rebasing, you will need to push the changes on the remote branch by typing:

```sh
git push --force origin 0-bd-test-unique-json-element
```

You need to force the push because the rebase rewrite to story, _i.e._ the order of the commits, of your branch.

Just check the opened pull-request that has been automatically updated after the push.
