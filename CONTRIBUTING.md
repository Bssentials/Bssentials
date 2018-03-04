(this file is an edited version of https://github.com/Bukkit/Bukkit/blob/master/CONTRIBUTING.md)

# How to Contribute

The Bssentials project prides itself on being community built and driven.  We love it when members of our community want to jump right in and get involved, so here's what you need to know.

## Quick Guide
- Does your proposed change [fit Bssentials's goals](#does-the-change-fit-bssentials-goals)?
- Fork the repository if you haven't done so already.
- Make your changes.
- Test your changes.
- Push to your fork and submit a pull request.

![Life Cycle of a Bssentials Improvement](http://i.imgur.com/Ed6T7AE.png)

## Getting Started
- You'll need a free [GitHub account](https://github.com/signup/free).
- Fork the repository on GitHub.


## Does the Change Fit Bssentials's Goals?
As a rough guideline, ask yourself the following questions to determine if your proposed change fits the Bssentials project's goals. Please remember that this is only a rough guideline and may or may not reflect the definitive answer to this question.
    
    One of the goals of the Bssentials project is to be able to operate within the limitations of the Bukkit and/or Vanilla environment. If your change results in or exposes the ability to, for example, crash the client/server when invalid data is set, it does not fit the Bssentials project's needs.
    
If you answered yes to any of these questions, chances are high your change does not fit the Bssentials project's goals and will most likely not be accepted. Regardless, there are a few other important questions you need to ask yourself before you start working on a change:

* Is this change reasonably supportable and maintainable?

* Is this change future proof?

## Making the Changes
* Create a branch on your fork where you'll be making your changes.
    * Name your branch something relevant to the change you are looking to make.
    * To create a branch in Git;
        * `git branch relevantBranchName`
        * Then checkout the new branch with `git checkout relevantBranchName`
* Check for unnecessary whitespace with `git diff --check` before committing.
* Make sure your code meets [our requirements](#code-requirements).
* Make sure your commit messages are in the [proper format](#commit-message-example).
* Test your changes to make sure it actually addresses the issue it should.
* --> Make sure your code compiles under Java 8, as that is what the project has to be built with. <--

### Code Requirements

* Java 8
