# List of things to change
- [x] Move .env reading code from App to Utils
- [ ] Optimize Table to avoid useless runs
  - [ ] Add the scroll() call to the if statement in both moveUp() and moveDown()
  - [ ] Avoid running scaleDown() when difference is 0 in resize()
- [ ] Improve some classes' structure (when develop is stable)
  - [ ] Move Utils to .misc
  - [ ] Rename main classes (App -> Launcher, Main -> Runtime)
