# Calculator App (Jetpack Compose + MVVM)

A simple calculator application built using Jetpack Compose, MVVM
architecture, and StateFlow, following Clean Architecture principles.

------------------------------------------------------------------------

## Architecture Overview

This project follows a unidirectional data flow pattern:

User Action\
→ Event\
→ ViewModel\
→ UseCase\
→ Operation\
→ StateFlow\
→ UI Recomposition

The UI is fully stateless and reacts only to state updates emitted from
the ViewModel.

------------------------------------------------------------------------

## Project Structure

### Presentation Layer

-   CalculatorScreen (Composable UI)\
-   CalculatorViewModel\
-   CalculatorUiState\
-   CalculatorEvent

### Domain Layer

-   CalculatorOperation (Abstraction)\
-   AddOperation\
-   SubtractOperation\
-   MultiplyOperation\
-   DivideOperation\
-   CalculatorUseCase

------------------------------------------------------------------------

## Big Picture Data Flow

User Click\
↓\
Composable (Stateless UI)\
↓\
onEvent(event)\
↓\
ViewModel\
↓\
Update MutableStateFlow (copy())\
↓\
StateFlow emits new state\
↓\
collectAsStateWithLifecycle()\
↓\
Composable recomposes\
↓\
UI updates

Key Principles: - UI does not store business state. - UI does not
perform calculations. - UI only renders state. - ViewModel is the single
source of truth.

------------------------------------------------------------------------

## Domain Layer

### CalculatorOperation

An abstraction layer for mathematical operations.

Benefits: - ViewModel does not depend on concrete implementations. - New
operations can be added without modifying ViewModel. - Follows the
Open/Closed Principle.

------------------------------------------------------------------------

### DivideOperation

-   Does not throw exceptions.
-   Does not crash.
-   Returns null for invalid operations (e.g., division by zero).
-   Safe failure handling.

------------------------------------------------------------------------

### CalculatorUseCase

Responsible for selecting and executing the correct operation.

Benefits: - Dependencies injected via constructor. - ViewModel does not
know operation details. - Follows Clean Architecture principles. -
Implements Dependency Inversion Principle (DIP).

------------------------------------------------------------------------

## ViewModel Layer

### CalculatorUiState

Immutable state holder for the entire UI.

All updates use:

\_uiState.value = current.copy(...)

Why immutability matters: - Predictable state updates - Thread-safe - No
race conditions - Easy to test - No direct mutation

------------------------------------------------------------------------

### StateFlow Setup

private val \_uiState = MutableStateFlow(CalculatorUiState())\
val uiState: StateFlow`<CalculatorUiState>`{=html} =
\_uiState.asStateFlow()

Concept: Controlled Mutability

-   UI can observe state.
-   UI cannot modify state.
-   Only ViewModel can mutate state.

------------------------------------------------------------------------

## Event System

The app uses a sealed class for events:

sealed class CalculatorEvent

Why sealed class? 
-   All possible events are known at compile-time. 
-   Exhaustive when expressions.
-   Prevents unhandled events. 
-   Type-safe and scalable.

------------------------------------------------------------------------

## Example Execution Flow

User presses:

7\
+\
5\
=

Step 1\
OnNumberClick("7")\
→ currentInput = "7"\
→ expression = "7"

Step 2\
OnOperatorClick("+")\
→ firstOperand = "7"\
→ operator = "+"\
→ expression = "7+"

Step 3\
OnNumberClick("5")\
→ currentInput = "5"\
→ expression = "7+5"

Step 4\
OnEqualClick\
→ Parse 7 and 5\
→ useCase.calculate("+", 7, 5)\
→ result = "= 12"

UI automatically recomposes and displays:

7+5\
= 12

------------------------------------------------------------------------

## Compose Layer

### collectAsStateWithLifecycle()

-   Lifecycle-aware
-   Stops collecting when app goes to background
-   No memory leaks
-   No manual cancellation needed

------------------------------------------------------------------------

### Stateless Composable Design

Composable functions are pure functions.

Input: - state - event handler

Output: - UI

There is: 
-   No business logic in UI.
-   No mutable business state.
-   No blocking calls
-   No heavy computation

UI is purely a renderer.

------------------------------------------------------------------------
