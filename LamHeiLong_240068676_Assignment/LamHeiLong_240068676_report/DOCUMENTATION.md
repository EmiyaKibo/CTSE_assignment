# Musical Ensembles Management System (MEMS)
## System Documentation

---

## Table of Contents
1. [System Overview](#system-overview)
2. [Architecture](#architecture)
3. [Design Patterns](#design-patterns)
4. [Class Documentation](#class-documentation)
5. [Usage Guide](#usage-guide)
6. [Build and Run Instructions](#build-and-run-instructions)

---

## System Overview

The Musical Ensembles Management System (MEMS) is a Java-based application for managing musical ensembles and their musicians. The system supports multiple ensemble types (Orchestra and Jazz Band) with different instrument roles, and provides complete undo/redo functionality for all operations.

### Key Features
- Create and manage multiple ensemble types (Orchestra, Jazz Band)
- Add, modify, and remove musicians from ensembles
- Role-based musician management (violinist, cellist, pianist, saxophonist, drummer)
- Complete undo/redo support for all operations
- Extensible architecture supporting new ensemble types without code modification

### Supported Ensemble Types
1. **Orchestra Ensemble**
   - Violinist (role = 1)
   - Cellist (role = 2)

2. **Jazz Band Ensemble**
   - Pianist (role = 1)
   - Saxophonist (role = 2)
   - Drummer (role = 3)

---

## Architecture

The system follows a layered architecture with clear separation of concerns:

```
┌─────────────────────────────────────┐
│     Presentation Layer (CLI)        │
│            MEMS.java                │
└─────────────────────────────────────┘
                 ↓
┌─────────────────────────────────────┐
│      Application Layer              │
│  HistoryManager, Context, Registry  │
└─────────────────────────────────────┘
                 ↓
┌─────────────────────────────────────┐
│       Business Logic Layer          │
│    Commands, Factories, Memento     │
└─────────────────────────────────────┘
                 ↓
┌─────────────────────────────────────┐
│        Domain Layer                 │
│    Ensemble, Musician, Memento      │
└─────────────────────────────────────┘
```

---

## Design Patterns

### 1. Factory Pattern
**Purpose:** Create ensemble and musician objects without specifying exact classes

**Implementation:**
- `EnsembleFactory` interface with `OrchestraEnsembleFactory` and `JazzBandEnsembleFactory`
- `MusicianFactory` interface with `OrchestraMusicianFactory` and `JazzBandMusicianFactory`
- `FactoryRegistry` manages all factories and supports registration of new types

**Benefits:**
- Supports Open-Closed Principle
- Easy to add new ensemble types
- Centralizes object creation logic

**Example:**
```java
EnsembleFactory factory = factoryRegistry.getEnsembleFactory("orchestra");
Ensemble ensemble = factory.createEnsemble("E001");
```

---

### 2. Command Pattern
**Purpose:** Encapsulate operations as objects to support undo/redo

**Implementation:**
- `Command` interface with `execute()`, `undo()`, and `getDescription()` methods
- Five concrete commands:
  - `CreateEnsembleCommand`
  - `AddMusicianCommand`
  - `ModifyMusicianInstrumentCommand`
  - `DeleteMusicianCommand`
  - `ChangeEnsembleNameCommand`

**Benefits:**
- Complete undo/redo functionality
- Operation history tracking
- Decouples operation execution from invocation

**Example:**
```java
Command command = new AddMusicianCommand(musician, ensembleId, registry, musicianRegistry, roleName);
historyManager.executeCommand(command);
// Later...
historyManager.undo(); // Reverses the operation
```

---

### 3. Memento Pattern
**Purpose:** Capture and restore object state without violating encapsulation

**Implementation:**
- `EnsembleMemento` stores ensemble state (musicians list and name)
- `Ensemble` class provides `createMemento()` and `restoreFromMemento()` methods
- Deep copying ensures state independence

**Benefits:**
- Enables reliable undo operations
- Preserves encapsulation
- State snapshots for commands

**Example:**
```java
EnsembleMemento memento = ensemble.createMemento();
// ... make changes ...
ensemble.restoreFromMemento(memento); // Restore previous state
```

---

### 4. Template Method Pattern
**Purpose:** Define algorithm skeleton in base class, let subclasses override specific steps

**Implementation:**
- `Ensemble` abstract class defines common behavior
- `OrchestraEnsemble` and `JazzBandEnsemble` override:
  - `updateMusicianRole()` - role validation logic
  - `showEnsemble()` - display format

**Benefits:**
- Code reuse for common ensemble operations
- Consistent interface across ensemble types
- Type-specific behavior customization

---

### 5. Open-Closed Principle
**Implementation:**
The system is open for extension but closed for modification:

**To add a new ensemble type (e.g., Choir):**
1. Create `ChoirEnsemble extends Ensemble`
2. Create `ChoirEnsembleFactory implements EnsembleFactory`
3. Create `ChoirMusicianFactory implements MusicianFactory`
4. Register factories in `FactoryRegistry`

**No existing code needs modification!**

---

## Class Documentation

### Domain Layer

#### Musician
**Purpose:** Represents a musician with an ID, name, and role

**Key Attributes:**
- `musicianID` - Unique identifier
- `mName` - Musician's name
- `role` - Instrument/role code (1, 2, or 3 depending on ensemble type)

**Key Methods:**
- `Musician(String mID)` - Constructor
- `Musician(Musician other)` - Copy constructor for deep copying (used by Memento)
- `getMID()`, `getName()`, `getRole()` - Getters
- `setName()`, `setRole()` - Setters
- `equals()`, `hashCode()` - For proper collection behavior
- `toString()` - Returns "ID, Name" format

**Design Notes:**
- Copy constructor is critical for Memento pattern
- equals() based on musicianID ensures uniqueness

---

#### Ensemble (Abstract)
**Purpose:** Base class for all ensemble types, defines common behavior

**Key Attributes:**
- `ensembleID` - Unique identifier
- `eName` - Ensemble name
- `musicians` - List of musicians (Vector for thread safety)

**Key Methods:**
- `addMusician(Musician m)` - Add musician to ensemble
- `dropMusician(Musician m)` - Remove musician from ensemble
- `getMusicians()` - Returns iterator over musicians
- `createMemento()` - Creates state snapshot
- `restoreFromMemento(EnsembleMemento)` - Restores state
- `updateMusicianRole(Musician, int)` - Abstract, role validation
- `showEnsemble()` - Abstract, display format

**Design Notes:**
- Template Method pattern - defines structure, subclasses customize
- Memento support for undo/redo
- Uses Vector for musicians list (legacy choice, could use ArrayList)

---

#### OrchestraEnsemble
**Purpose:** Orchestra-specific ensemble implementation

**Role Constants:**
- `VIOLINIST_ROLE = 1`
- `CELLIST_ROLE = 2`

**Key Methods:**
- `updateMusicianRole()` - Validates role is 1 or 2
- `showEnsemble()` - Displays violinists, then cellists, "NIL" if empty

**Display Format:**
```
Orchestra Ensemble [Name] ([ID])
Violinist:
[musician list or NIL]
Cellist:
[musician list or NIL]
```

---

#### JazzBandEnsemble
**Purpose:** Jazz band-specific ensemble implementation

**Role Constants:**
- `PIANIST_ROLE = 1`
- `SAXOPHONIST_ROLE = 2`
- `DRUMMER_ROLE = 3`

**Key Methods:**
- `updateMusicianRole()` - Validates role is 1, 2, or 3
- `showEnsemble()` - Displays pianists, saxophonists, drummers, "NIL" if empty

**Display Format:**
```
Jazz Band Ensemble [Name] ([ID])
Pianist:
[musician list or NIL]
Saxophonist:
[musician list or NIL]
Drummer:
[musician list or NIL]
```

---

#### EnsembleMemento
**Purpose:** Stores ensemble state for undo/redo operations

**Key Attributes:**
- `musicians` - Deep copy of musicians list
- `name` - Ensemble name at time of snapshot

**Key Methods:**
- `EnsembleMemento(List<Musician>, String)` - Constructor with deep copy
- `getMusicians()` - Returns stored musicians
- `getName()` - Returns stored name

**Design Notes:**
- Deep copies musicians using copy constructor
- Immutable after creation
- Used by Command pattern for undo operations

---

### Factory Layer

#### EnsembleFactory (Interface)
**Purpose:** Factory interface for creating ensembles

**Methods:**
- `Ensemble createEnsemble(String eID)` - Create ensemble instance
- `String getEnsembleType()` - Return type name ("orchestra", "jazz band")

---

#### OrchestraEnsembleFactory
**Purpose:** Creates orchestra ensemble instances

**Implementation:**
- Returns new `OrchestraEnsemble` instance
- Type name: "orchestra"

---

#### JazzBandEnsembleFactory
**Purpose:** Creates jazz band ensemble instances

**Implementation:**
- Returns new `JazzBandEnsemble` instance
- Type name: "jazz band"

---

#### MusicianFactory (Interface)
**Purpose:** Factory interface for creating musicians with validation

**Methods:**
- `Musician createMusician(String mID, String name, int role)` - Create and validate
- `boolean isValidRole(int role)` - Check if role is valid for this ensemble type
- `String getRoleName(int role)` - Get human-readable role name

---

#### OrchestraMusicianFactory
**Purpose:** Creates musicians for orchestra ensembles

**Valid Roles:**
- 1 = violinist
- 2 = cellist

**Implementation:**
- Validates role before creating musician
- Throws `IllegalArgumentException` for invalid roles

---

#### JazzBandMusicianFactory
**Purpose:** Creates musicians for jazz band ensembles

**Valid Roles:**
- 1 = pianist
- 2 = saxophonist
- 3 = drummer

**Implementation:**
- Validates role before creating musician
- Throws `IllegalArgumentException` for invalid roles

---

#### FactoryRegistry
**Purpose:** Central registry for all factory types, supports Open-Closed Principle

**Key Attributes:**
- `ensembleFactories` - Map of ensemble type to factory
- `musicianFactories` - Map of ensemble type to musician factory
- `commandFactories` - Map of command type to command factory (extensibility)

**Key Methods:**
- `registerEnsembleFactory(String type, EnsembleFactory)` - Register new ensemble type
- `registerMusicianFactory(String type, MusicianFactory)` - Register new musician factory
- `getEnsembleFactory(String type)` - Retrieve factory by type
- `getMusicianFactory(String type)` - Retrieve musician factory by type
- `registerDefaultFactories()` - Registers orchestra and jazz band factories

**Registered Types:**
- "orchestra", "o" → Orchestra factories
- "jazz", "j" → Jazz band factories

**Design Notes:**
- Enables adding new ensemble types without modifying existing code
- Supports both short and long type names for user convenience

---

### Command Layer

#### Command (Interface)
**Purpose:** Encapsulates operations as objects for undo/redo support

**Methods:**
- `boolean execute()` - Perform the operation, return success
- `boolean undo()` - Reverse the operation, return success
- `String getDescription()` - Return human-readable description

---

#### CreateEnsembleCommand
**Purpose:** Creates a new ensemble and sets it as current

**Key Attributes:**
- `factory` - EnsembleFactory to create the ensemble
- `ensembleId`, `name` - Ensemble details
- `registry` - EnsembleRegistry for registration
- `context` - CurrentEnsembleContext to update
- `previousEnsembleId` - For undo restoration

**Behavior:**
- **Execute:** Creates ensemble, registers it, sets as current, saves previous current
- **Undo:** Unregisters ensemble, restores previous current ensemble
- **Description:** "Create [type] ensemble, [ID], [name]"

---

#### AddMusicianCommand
**Purpose:** Adds a musician to an ensemble

**Key Attributes:**
- `musician` - Musician to add
- `ensembleId` - Target ensemble
- `registry`, `musicianRegistry` - For registration
- `memento` - State snapshot for undo
- `roleName` - Human-readable role name

**Behavior:**
- **Execute:** Creates memento, adds musician to ensemble, registers musician
- **Undo:** Restores ensemble from memento, unregisters musician
- **Description:** "Add musician, [ID], [name], [role]"

**Design Notes:**
- Uses Memento pattern to preserve ensemble state
- Ensures musician is registered globally

---

#### ModifyMusicianInstrumentCommand
**Purpose:** Changes a musician's role/instrument

**Key Attributes:**
- `musicianId` - Musician to modify
- `newRole` - New role code
- `ensembleId` - Ensemble containing musician
- `registry`, `musicianRegistry` - For lookup
- `memento` - State snapshot for undo
- `roleName` - Human-readable role name

**Behavior:**
- **Execute:** Creates memento, updates musician role via ensemble
- **Undo:** Restores ensemble from memento
- **Description:** "Modify musician's instrument, [ID], [role]"

---

#### DeleteMusicianCommand
**Purpose:** Removes a musician from an ensemble

**Key Attributes:**
- `musicianId` - Musician to delete
- `ensembleId` - Ensemble containing musician
- `registry`, `musicianRegistry` - For lookup and unregistration
- `memento` - State snapshot for undo

**Behavior:**
- **Execute:** Creates memento, removes musician from ensemble, unregisters musician
- **Undo:** Restores ensemble from memento, re-registers musician
- **Description:** "Delete musician, [ID]"

**Design Notes:**
- Undo must re-register the musician from restored ensemble state

---

#### ChangeEnsembleNameCommand
**Purpose:** Changes an ensemble's name

**Key Attributes:**
- `ensembleId` - Ensemble to rename
- `newName` - New name
- `registry` - For ensemble lookup
- `oldName` - For undo restoration

**Behavior:**
- **Execute:** Saves old name, sets new name
- **Undo:** Restores old name
- **Description:** "Change ensemble's name, [ID], [newName]"

**Design Notes:**
- Simpler than other commands - no memento needed, just name swap

---

### Registry Layer

#### EnsembleRegistry
**Purpose:** Centralized storage and lookup for ensembles

**Key Attributes:**
- `ensembles` - Map<String, Ensemble> keyed by ensemble ID

**Key Methods:**
- `register(Ensemble)` - Add ensemble, throws exception if ID exists
- `unregister(String ensembleId)` - Remove ensemble
- `find(String ensembleId)` - Retrieve ensemble by ID
- `listAll()` - Get all ensembles
- `exists(String ensembleId)` - Check if ensemble exists

**Design Notes:**
- Prevents duplicate ensemble IDs
- Single source of truth for ensemble storage

---

#### MusicianRegistry
**Purpose:** Centralized storage and lookup for musicians

**Key Attributes:**
- `musicians` - Map<String, Musician> keyed by musician ID

**Key Methods:**
- `register(Musician)` - Add musician
- `unregister(String musicianId)` - Remove musician
- `find(String musicianId)` - Retrieve musician by ID
- `exists(String musicianId)` - Check if musician exists

**Design Notes:**
- Tracks all musicians across all ensembles
- Enables global musician lookup

---

### Application Layer

#### HistoryManager
**Purpose:** Manages command execution and undo/redo stacks

**Key Attributes:**
- `undoStack` - Stack<Command> of executed commands
- `redoStack` - Stack<Command> of undone commands

**Key Methods:**
- `executeCommand(Command)` - Execute command, push to undo stack, clear redo stack
- `undo()` - Pop from undo stack, call undo(), push to redo stack, return description
- `redo()` - Pop from redo stack, call execute(), push to undo stack, return description
- `getUndoList()` - Get list of undo command descriptions
- `getRedoList()` - Get list of redo command descriptions (reversed order)

**Behavior:**
- Executing a new command clears the redo stack
- Undo moves command from undo to redo stack
- Redo moves command from redo to undo stack

**Design Notes:**
- Classic undo/redo implementation with two stacks
- Returns null when stacks are empty

---

#### CurrentEnsembleContext
**Purpose:** Tracks the currently selected ensemble

**Key Attributes:**
- `currentEnsembleId` - ID of current ensemble (null if none)

**Key Methods:**
- `setCurrentEnsemble(String ensembleId)` - Set current ensemble
- `getCurrentEnsembleId()` - Get current ensemble ID
- `hasCurrentEnsemble()` - Check if current ensemble is set

**Design Notes:**
- Simple state holder
- Used by commands and CLI to know which ensemble is active

---

### Presentation Layer

#### MEMS (Main Application)
**Purpose:** Command-line interface and application coordinator

**Key Attributes:**
- `ensembleRegistry` - Ensemble storage
- `musicianRegistry` - Musician storage
- `context` - Current ensemble tracker
- `factoryRegistry` - Factory manager
- `historyManager` - Undo/redo manager
- `scanner` - User input reader

**Key Methods:**
- `run()` - Main application loop
- `displayMenu()` - Show menu and current ensemble
- `processCommand(String)` - Route command to handler
- `createEnsemble()` - Handle 'c' command
- `setCurrentEnsemble()` - Handle 's' command
- `addMusician()` - Handle 'a' command
- `modifyMusicianInstrument()` - Handle 'm' command
- `deleteMusician()` - Handle 'd' command
- `showEnsemble()` - Handle 'se' command
- `displayAllEnsembles()` - Handle 'sa' command
- `changeEnsembleName()` - Handle 'cn' command
- `undo()` - Handle 'u' command
- `redo()` - Handle 'r' command
- `listUndoRedo()` - Handle 'l' command
- `main(String[])` - Application entry point

**Command Reference:**
- `c` - Create ensemble
- `s` - Set current ensemble
- `a` - Add musician
- `m` - Modify musician's instrument
- `d` - Delete musician
- `se` - Show ensemble
- `sa` - Display all ensembles
- `cn` - Change ensemble's name
- `u` - Undo
- `r` - Redo
- `l` - List undo/redo
- `x` - Exit system

**Design Notes:**
- Coordinates all system components
- Handles user interaction and input validation
- Creates and executes commands via HistoryManager

---

## Usage Guide

### Starting the Application

Run the application using:
```bash
java MEMS
```

Or use the provided batch file:
```bash
run.bat
```

### Creating an Ensemble

1. Enter command: `c`
2. Choose ensemble type: `o` (orchestra) or `j` (jazz band)
3. Enter ensemble ID (e.g., `E001`)
4. Enter ensemble name (e.g., `Vienna Philharmonic`)

**Example:**
```
Please enter command [ c | s | a | m | d | se | sa | cn | u | r | l | x ] :- c
Enter music type (o = orchestra | j = jazz band) :- o
Ensemble ID:- E001
Ensemble Name:- Vienna Philharmonic
Orchestra ensemble is created.
Current ensemble is changed to E001.
```

---

### Adding a Musician

1. Ensure an ensemble is set as current
2. Enter command: `a`
3. Enter musician info: `ID, Name` (comma-separated)
4. Choose instrument role (numbers vary by ensemble type)

**Example (Orchestra):**
```
Please enter command [ c | s | a | m | d | se | sa | cn | u | r | l | x ] :- a
Please input musician information (id, name):- M001, Ludwig van Beethoven
Instrument (1 = violinist | 2 = cellist ):- 1
Musician is added.
```

**Example (Jazz Band):**
```
Please enter command [ c | s | a | m | d | se | sa | cn | u | r | l | x ] :- a
Please input musician information (id, name):- M003, Miles Davis
Instrument (1 = pianist | 2 = saxophonist | 3 = drummer):- 1
Musician is added.
```

---

### Modifying a Musician's Instrument

1. Ensure an ensemble is set as current
2. Enter command: `m`
3. Enter musician ID
4. Choose new instrument role

**Example:**
```
Please enter command [ c | s | a | m | d | se | sa | cn | u | r | l | x ] :- m
Please input musician ID:- M001
Instrument (1 = violinist | 2 = cellist ):- 2
Instrument is updated.
```

---

### Deleting a Musician

1. Ensure an ensemble is set as current
2. Enter command: `d`
3. Enter musician ID

**Example:**
```
Please enter command [ c | s | a | m | d | se | sa | cn | u | r | l | x ] :- d
Please input musician ID:- M002
Musician is deleted.
```

---

### Showing Current Ensemble

Enter command: `se`

**Example Output (Orchestra):**
```
Orchestra Ensemble Vienna Philharmonic (E001)
Violinist:
M001, Ludwig van Beethoven
Cellist:
M002, Wolfgang Mozart
```

**Example Output (Jazz Band):**
```
Jazz Band Ensemble Blue Note Quintet (E002)
Pianist:
M003, Miles Davis
Saxophonist:
M004, John Coltrane
Drummer:
M005, Art Blakey
```

---

### Displaying All Ensembles

Enter command: `sa`

**Example Output:**
```
Orchestra Ensemble Vienna Philharmonic (E001)
Jazz Band Ensemble Blue Note Quintet (E002)
```

---

### Setting Current Ensemble

1. Enter command: `s`
2. Enter ensemble ID

**Example:**
```
Please enter command [ c | s | a | m | d | se | sa | cn | u | r | l | x ] :- s
Please input ensemble ID:- E001
Changed current ensemble to E001.
```

---

### Changing Ensemble Name

1. Ensure an ensemble is set as current
2. Enter command: `cn`
3. Enter new name

**Example:**
```
Please enter command [ c | s | a | m | d | se | sa | cn | u | r | l | x ] :- cn
Please input new name of the current ensemble:- Royal Vienna Orchestra
Ensemble's name is updated.
```

---

### Undo Operation

Enter command: `u`

**Example:**
```
Please enter command [ c | s | a | m | d | se | sa | cn | u | r | l | x ] :- u
Command (Change ensemble's name, E001, Royal Vienna Orchestra) is undone.
```

---

### Redo Operation

Enter command: `r`

**Example:**
```
Please enter command [ c | s | a | m | d | se | sa | cn | u | r | l | x ] :- r
Command (Change ensemble's name, E001, Royal Vienna Orchestra) is redone.
```

---

### Listing Undo/Redo History

Enter command: `l`

**Example Output:**
```
Undo List
Create orchestra ensemble, E001, Vienna Philharmonic
Add musician, M001, Ludwig van Beethoven, violinist
Add musician, M002, Wolfgang Mozart, cellist
Create jazz band ensemble, E002, Blue Note Quintet
Add musician, M003, Miles Davis, pianist
-- End of undo list --

Redo List
-- End of redo list --
```

---

### Exiting the Application

Enter command: `x`

---

## Build and Run Instructions

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Command line access (CMD or PowerShell on Windows)

### Compilation

**Option 1: Using batch file**
```bash
run.bat
```
This will compile and run the application automatically.

**Option 2: Manual compilation**
```bash
javac *.java
java MEMS
```

### Running Tests

**Automated test with predefined inputs:**
```bash
test.bat
```

This runs a comprehensive test scenario that:
1. Creates an orchestra ensemble
2. Adds musicians to orchestra
3. Shows orchestra ensemble
4. Creates a jazz band ensemble
5. Adds musicians to jazz band
6. Shows jazz band ensemble
7. Sets current ensemble
8. Displays all ensembles
9. Modifies musician instrument
10. Deletes musician
11. Changes ensemble name
12. Tests undo/redo operations

---

## Error Handling

### Common Errors and Solutions

**"No current ensemble set!"**
- **Cause:** Trying to add/modify/delete musician or show ensemble without setting current ensemble
- **Solution:** Use `c` to create an ensemble or `s` to set an existing one as current

**"Ensemble [ID] is not found!!"**
- **Cause:** Trying to set a non-existent ensemble as current
- **Solution:** Check ensemble ID with `sa` command

**"Ensemble [ID] already exists"**
- **Cause:** Trying to create an ensemble with duplicate ID
- **Solution:** Use a different ensemble ID

**"Invalid role for orchestra: [role]"**
- **Cause:** Trying to assign invalid role to orchestra musician
- **Solution:** Use role 1 (violinist) or 2 (cellist) for orchestra

**"Invalid role for jazz band: [role]"**
- **Cause:** Trying to assign invalid role to jazz band musician
- **Solution:** Use role 1 (pianist), 2 (saxophonist), or 3 (drummer) for jazz band

**"Nothing to undo!"**
- **Cause:** Undo stack is empty
- **Solution:** Perform some operations first

**"Nothing to redo!"**
- **Cause:** Redo stack is empty
- **Solution:** Undo some operations first

---

## Extending the System

### Adding a New Ensemble Type

To add a new ensemble type (e.g., Choir), follow these steps:

1. **Create Ensemble Class**
```java
public class ChoirEnsemble extends Ensemble {
    private final int SOPRANO_ROLE = 1;
    private final int ALTO_ROLE = 2;
    private final int TENOR_ROLE = 3;
    private final int BASS_ROLE = 4;
    
    public ChoirEnsemble(String eID) {
        super(eID);
    }
    
    @Override
    public void updateMusicianRole(Musician m, int role) {
        if (role >= 1 && role <= 4) {
            m.setRole(role);
        }
    }
    
    @Override
    public void showEnsemble() {
        // Implementation for displaying choir
    }
}
```

2. **Create Ensemble Factory**
```java
public class ChoirEnsembleFactory implements EnsembleFactory {
    @Override
    public Ensemble createEnsemble(String eID) {
        return new ChoirEnsemble(eID);
    }
    
    @Override
    public String getEnsembleType() {
        return "choir";
    }
}
```

3. **Create Musician Factory**
```java
public class ChoirMusicianFactory implements MusicianFactory {
    @Override
    public Musician createMusician(String mID, String name, int role) {
        if (!isValidRole(role)) {
            throw new IllegalArgumentException("Invalid role for choir: " + role);
        }
        Musician m = new Musician(mID);
        m.setName(name);
        m.setRole(role);
        return m;
    }
    
    @Override
    public boolean isValidRole(int role) {
        return role >= 1 && role <= 4;
    }
    
    @Override
    public String getRoleName(int role) {
        switch (role) {
            case 1: return "soprano";
            case 2: return "alto";
            case 3: return "tenor";
            case 4: return "bass";
            default: return "unknown";
        }
    }
}
```

4. **Register in FactoryRegistry**
```java
// In FactoryRegistry.registerDefaultFactories()
registerEnsembleFactory("choir", new ChoirEnsembleFactory());
registerEnsembleFactory("ch", new ChoirEnsembleFactory());
registerMusicianFactory("choir", new ChoirMusicianFactory());
```

5. **Update MEMS CLI** (optional)
Add "ch = choir" to the menu display if desired.

**No other code modifications needed!** This demonstrates the Open-Closed Principle.

---

## System Limitations

1. **Single User:** No multi-user support or concurrent access
2. **No Persistence:** Data is lost when application exits (no database or file storage)
3. **No Validation:** Limited input validation (e.g., empty names, special characters)
4. **Fixed Roles:** Each ensemble type has predefined roles that cannot be customized at runtime
5. **No Musician Transfer:** Cannot move musicians between ensembles (must delete and re-add)
6. **Memory-Based:** All data stored in memory, limited by available RAM

---

## Future Enhancements

1. **Persistence Layer:** Add database or file storage for data persistence
2. **GUI Interface:** Replace CLI with graphical user interface
3. **Advanced Search:** Search musicians by name, role, or ensemble
4. **Musician Transfer:** Move musicians between ensembles
5. **Performance Scheduling:** Add concert/performance management
6. **Reporting:** Generate ensemble reports and statistics
7. **Import/Export:** Support for CSV or JSON data import/export
8. **Role Customization:** Allow custom roles per ensemble instance
9. **Validation:** Enhanced input validation and error messages
10. **Multi-language Support:** Internationalization for different languages

---

## Credits

**System:** Musical Ensembles Management System (MEMS)
**Version:** 1.0
**Date:** November 2024

**Design Patterns Implemented:**
- Factory Pattern
- Command Pattern
- Memento Pattern
- Template Method Pattern
- Open-Closed Principle

---

## License

This is an educational project for academic purposes.

---

*End of Documentation*
