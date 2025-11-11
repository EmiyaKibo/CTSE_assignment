# MEMS - Musical Ensembles Management System
## Clean Folder Structure with Intuitive Command Interface

This project demonstrates design patterns with a user-friendly CLI that supports both short aliases (for power users) and full command names (for beginners).

## 🎮 Available Commands

The system supports **multiple aliases** for each command - use whichever you prefer!

| Short | Full Name | Description | Requires Ensemble |
|-------|-----------|-------------|-------------------|
| `c` | `create` | Create a new ensemble | No |
| `sw` | `switch` | Switch to a different ensemble | No |
| `a` | `add` | Add a musician to current ensemble | Yes * |
| `m` | `modify` | Modify a musician's instrument | Yes * |
| `rm` | `remove` | Remove a musician from ensemble | Yes * |
| `rn` | `rename` | Rename current ensemble | Yes * |
| `s` | `show` | Show current ensemble details | Yes * |
| `ls` | `list` | List all ensembles | No |
| `u` | `undo` | Undo last action | No |
| `r` | `redo` | Redo last undone action | No |
| `h`, `?` | `help` | Show help message with all commands | No |
| `q` | `quit` | Exit the program | No |

**\* Requires an ensemble to be set** - use `switch` to select one first

### 💡 Usage Examples:

**For Beginners** (use full names):
```
> create       (Create new ensemble)
> switch       (Switch to ensemble)
> add          (Add musician)
> help         (Show all commands)
```

**For Power Users** (use short aliases):
```
> c            (Create new ensemble)
> sw           (Switch to ensemble)
> a            (Add musician)
> ?            (Show all commands)
```

**Both work the same!** Commands are case-insensitive.

## 📁 Folder Structure

```
LamHeiLong_240068676_source/
│
├── 📄 MEMS.java                    # Main application entry point
├── 📄 run.bat                      # Compile and run script
│
├── 📂 domain/                      # Domain Model (Entities)
│   ├── Musician.java
│   ├── Ensemble.java
│   ├── OrchestraEnsemble.java
│   ├── JazzBandEnsemble.java
│   └── EnsembleMemento.java
│
├── 📂 command/                     # Command Pattern
│   ├── Command.java               # Interface
│   ├── CommandEntry.java          # Command wrapper for list-based routing
│   ├── CreateEnsembleCommand.java
│   ├── AddMusicianCommand.java
│   ├── ModifyMusicianInstrumentCommand.java
│   ├── DeleteMusicianCommand.java
│   └── ChangeEnsembleNameCommand.java
│
├── 📂 factory/                     # Factory Pattern
│   ├── CommandFactory.java        # Interface
│   ├── EnsembleFactory.java       # Interface
│   ├── MusicianFactory.java       # Interface
│   ├── CreateEnsembleCommandFactory.java
│   ├── AddMusicianCommandFactory.java
│   ├── ModifyMusicianInstrumentCommandFactory.java
│   ├── DeleteMusicianCommandFactory.java
│   ├── ChangeEnsembleNameCommandFactory.java
│   ├── OrchestraEnsembleFactory.java
│   ├── JazzBandEnsembleFactory.java
│   ├── OrchestraMusicianFactory.java
│   └── JazzBandMusicianFactory.java
│
├── 📂 registry/                    # Registries & Managers
│   ├── EnsembleRegistry.java
│   ├── MusicianRegistry.java
│   ├── FactoryRegistry.java
│   ├── CurrentEnsembleContext.java
│   └── HistoryManager.java
│
├── 📂 test/                        # Test Files
│   ├── test_input.txt
│   ├── test_simple.txt
│   └── test.bat
│
└── 📂 docs/                        # Documentation
    ├── DOCUMENTATION.md
    ├── CODE_COMMENTS_SUMMARY.txt
    ├── MEMS_ClassDiagram.md
    ├── MEMS_ClassDiagram.drawio
    └── Assignment PDFs
```

## 🎯 Design Patterns by Folder

### Domain Layer (`domain/`)
- **Template Method Pattern**: `Ensemble` (abstract base class)
- **Memento Pattern**: `EnsembleMemento` (state preservation)

### Command Layer (`command/`)
- **Command Pattern**: All command classes for undo/redo support
- **List-based Routing**: `CommandEntry` for flexible command matching

### Factory Layer (`factory/`)
- **Factory Pattern**: Ensemble and Musician factories
- **Abstract Factory**: Command factories for creating command objects

### Registry Layer (`registry/`)
- **Registry Pattern**: EnsembleRegistry, MusicianRegistry
- **Context Pattern**: CurrentEnsembleContext
- **History/Memento Manager**: HistoryManager for undo/redo

## 🔧 How to Compile and Run

### Quick Start:
```batch
run.bat
```
This compiles all files and runs the application.

### Manual Commands:

**Compile:**
```batch
javac -d . domain/*.java factory/*.java command/*.java registry/*.java MEMS.java
```

**Run:**
```batch
java MEMS
```

**Run with Test Input:**
```batch
type test\test_input.txt | java MEMS
```

## 📚 Why No Packages?

This project uses **folder organization WITHOUT Java packages** for these reasons:

### ✅ Advantages (Why we did it):
1. **Simpler for students** - No package declarations needed
2. **Easier compilation** - Single javac command compiles everything
3. **No classpath issues** - Works immediately without configuration
4. **Assignment-friendly** - Easier to submit and grade
5. **Clear organization** - Folders provide visual structure

### ⚠️ When You WOULD Need Packages:

You would need package declarations if:

1. **Name Conflicts**: Two classes with the same name in different folders
   ```java
   // domain/Command.java vs command/Command.java
   // Without packages, this causes a conflict!
   ```

2. **Access Control**: You want package-private visibility
   ```java
   class InternalHelper { }  // Only visible within package
   ```

3. **Large Projects**: 100+ classes across multiple modules
4. **Library/Framework**: Code that will be imported by others
5. **Team Projects**: Different teams working on different modules

### 🎓 Learning Point: Folder vs Package

```
WITHOUT PACKAGES (Our approach):
✓ Folder: domain/Musician.java
✓ Code:   public class Musician { }
✓ Compile: javac domain/*.java
✓ Run:    java Musician

WITH PACKAGES (Enterprise approach):
✓ Folder: domain/Musician.java
✓ Code:   package domain;
          public class Musician { }
✓ Compile: javac -d bin domain/*.java
✓ Run:    java -cp bin domain.Musician
```

## 📋 File Count Summary

- **Active Files**: 30 Java files
  - Domain: 5 files
  - Command: 7 files  
  - Factory: 12 files
  - Registry: 5 files
  - Main: 1 file (MEMS.java)

- **Build Script**: 1 file (run.bat - simple compile and run)

- **Documentation**: 7 files

- **Test Files**: 3 files

## 🎯 Benefits of This Organization

1. ✅ **Easy to Navigate** - Files grouped by responsibility
2. ✅ **Clear Separation** - Each folder has a specific purpose
3. ✅ **Pattern Visibility** - Design patterns are obvious from structure
4. ✅ **Maintainable** - Easy to find and modify files
5. ✅ **Educational** - Structure teaches good organization
6. ✅ **No Package Complexity** - Simpler for academic assignments

---

**Author**: Lam Hei Long (240068676)  
**Course**: ITP4507 - Contemporary Technologies in Software Engineering  
**Date**: November 2025
