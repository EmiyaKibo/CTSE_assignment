# MEMS - Quick Reference Card

## 🚀 Quick Start

```batch
run.bat
```

Type `help` or `?` anytime to see all commands.

## 📋 Command Cheat Sheet

### Essential Commands (Beginners)
```
create    - Create new ensemble
switch    - Select an ensemble to work with
add       - Add musician to ensemble
show      - Display current ensemble
list      - Show all ensembles
help      - Show all commands
quit      - Exit program
```

### Power User Shortcuts
```
c    - Create
sw   - Switch
a    - Add
s    - Show
ls   - List
h/?  - Help
q    - Quit
```

### Editing & Management
```
modify / m     - Change musician's instrument
remove / rm    - Delete a musician
rename / rn    - Rename current ensemble
```

### Undo/Redo
```
undo / u      - Undo last action
redo / r      - Redo last undone action
```

## 🎯 Typical Workflow

1. **Create an ensemble**
   ```
   > create
   Enter music type (o = orchestra | j = jazz band) :- o
   Ensemble ID:- 1
   Ensemble Name:- My Orchestra
   ```

2. **Switch to it** (if needed)
   ```
   > switch
   Please input ensemble ID:- 1
   ```

3. **Add musicians**
   ```
   > add
   Please input musician information (id, name):- 101 John Doe
   Please input the instrument (violinist | cellist):- violinist
   ```

4. **View ensemble**
   ```
   > show
   Orchestra Ensemble 1 (My Orchestra)
   Violinist: 101 John Doe
   Cellist: NIL
   ```

5. **Make changes**
   ```
   > modify               # Change instrument
   > rename               # Rename ensemble
   > remove               # Delete musician
   > undo                 # Undo if needed
   ```

## 🎵 Ensemble Types

### Orchestra
- Instruments: **Violinist**, **Cellist**
- Create with: `o` when prompted

### Jazz Band
- Instruments: **Pianist**, **Saxophonist**, **Drummer**
- Create with: `j` when prompted

## ⭐ Commands That Need Active Ensemble

These require an ensemble to be selected first (marked with * in help):

- `add` - Add musician
- `modify` - Modify instrument
- `remove` - Delete musician
- `show` - Show details
- `rename` - Rename ensemble

**Solution**: Use `switch` to select an ensemble first!

## 💡 Pro Tips

1. **All commands are case-insensitive**
   - `HELP`, `help`, `HeLp` all work!

2. **Multiple ways to access help**
   - Type: `h`, `help`, or `?`

3. **Unix-style shortcuts**
   - `ls` = list (like Linux)
   - `rm` = remove (like Linux)

4. **Keyboard shortcuts**
   - `u` and `r` are next to each other for undo/redo
   - `q` for quick exit

5. **Error recovery**
   - Made a mistake? Use `undo`
   - Changed your mind? Use `redo`
   - Can undo/redo multiple times!

## 🐛 Common Issues

**"No current ensemble set!"**
- Solution: Use `switch` to select an ensemble

**"Invalid command!"**
- Solution: Type `help` to see available commands

**"Ensemble X is not found!"**
- Solution: Use `list` to see all ensemble IDs

## 📞 Command Summary Table

| Short | Long | Action | Needs Ensemble? |
|-------|------|--------|-----------------|
| c | create | Create ensemble | No |
| sw | switch | Select ensemble | No |
| a | add | Add musician | Yes |
| m | modify | Change instrument | Yes |
| rm | remove | Delete musician | Yes |
| rn | rename | Rename ensemble | Yes |
| s | show | Display ensemble | Yes |
| ls | list | List all | No |
| u | undo | Undo last | No |
| r | redo | Redo last | No |
| h, ? | help | Show help | No |
| q | quit | Exit | No |

---

**Remember**: When in doubt, type `help`!

**Assignment**: ITP4507 - Lam Hei Long (240068676)
