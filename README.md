# GP22-group16

Supervision 

- 30 min a week 
- Be prepared, the supervisor havnt prepared anything 
- Questions on teams for the supervisor 

Office hours: 
- drop in an ask anything 

Supervision room (pending)

Supervision for our group:
- 15 pm

Open office hours: 
- Friday @ 12-14

Advice: 
- Manage your project early and well
- Establish a good workflow with git:
    Individual dev branches
    pull request to merge into master
- Set up a good .gitignore before anything else
  config.txt, data/, build/, .gradle/ .... more? 
- Document as you code


Get familiar with the code first and make branches
Agree on a class design
- then we can go with the refactoring among us. 

_________________
## Git Workshop Tips
_________________
- If you use VSCODE gui to add or delete or something, you press plus under stage and press okay.
- Work on different files in IP project, otherwise we could end up with merge conflicts

- Do smaller commits often , then you only loose as little as possible after changes makes it easier

- gives you all the branches :
    git branch
- create new branch :
 git checkout -b <branch "name">
- Change branches :
git checkout <"nameofbranch">
- Deletes branch :
 git branch -d "nameofbranch"

## Remember before committing and pushing your local branch

- git checkout main
- git pull
- git checkout <your local branchname>
- git merge main
    
## Notes to ourselves: 
    
    - Dont change too much code at first, move around and see what parts can be split into methods 

    - If one method contains multiple functions, divide them 
    - Look at each line, and divide them into methods (even though it will look primitive, think of the javadoc, the explanation in javadoc should actually just be the         method name in the best situation. 
    
    - If you change something, the program should still be able to work, otherwise the system is too coupled
    - If a method is responsible for web stuff, keep it in the webserver class.
    

