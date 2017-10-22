# SERP STAT APP

## React Router V4
    * Reference: https://reacttraining.com/react-router/web/example/modal-gallery

## Get Url param
    * Reference: https://egghead.io/lessons/javascript-redux-using-withrouter-to-inject-the-params-into-connected-components


## ES6
* Arrow Function
* Block level scoping with let and const
* Destructuring


### Arrow Function

* Multi parameter
```javascript
const multiple = (x, y) => { return x * y };
```
or
```javascript
const multiple = (x, y) => x * y;
```

* One parameter
```javascript
const phraseSplitterES6 = phrase => phrase.split(" ");
```

* No parameter
```javascript
const docLogES6 = () => { console.log("arrow function example"); }
```

### Block level scoping with let and const
Reference: http://www.benmvp.com/learning-es6-block-level-scoping-let-const/
`let` is the new `var`. ES6 provides two new ways for declaring variables: `let` and `const`. These pretty much replace the ES3 or ES5 way of declaring variables using `var`. By using block-level scoping, these two keywords help developers avoid common mistakes they make not because they write bad code, but because they don’t fully understand the idiosyncrasies of how JavaScript handles variables.

```javascript
function simpleExample(value) {
	const constValue = value;

	if (value) {
		var varValue = value;
		let letValue = value;

		console.log('inside block', varValue, letValue);
	}

	console.log('outside block');

	// varValue is available even though it was defined
	// in if-block because it was "hoisted" to function scope
	console.log(varValue);

	try {
		// letValue is a ReferenceError because it
		// was defined w/in if-block
		console.log(letValue);
	}
	catch (e) {
		// e is a ReferenceError
		console.log('letValue not accessible', e);
	}

	// SyntaxError to try and update a variable
	// declared via const
	//constValue += 1;
}

simpleExample(2);
```

`let` works similarly to `var`, but the variable it declares is block-scoped; it only exists within the current block.

```javascript
function letExample(value) {
	if (value) {
		let letValue = value;

		console.log('inside block', letValue);

		// redeclaration of letValue would be a SyntaxError
		let letValue = 'foo';
	}

	try {
		// Accessing letValue is a ReferenceError because it
		// was defined w/in if-block
		console.log(letValue);

		// if we get here, it means that the JS engine didn't
		// throw an exception, which means that the engine
		// (or transpiled code) did not faithfully reproduce
		// how let should work
		console.log('let not faithfully handled');
	}
	catch (e) {
		// e is a ReferenceError
		console.log('letValue not accessible', e);
	}
}
letExample(2);
```

Shadowing variables with `let`
```javascript
function letShadowExample() {
	let x = 15;

	if (true) {
		// this x "shadows" the x defined in the outer scope.
		// this new x just exists within the scope of the
		// if-block
		let x = 21;

		// x should be 21
		console.log('x inner block', x);
	}

	// x should be 15
	console.log('x outer block', x);
}
letShadowExample();
```

Shadowing variables with `let`
```javascript
{
	// Uninitialized “binding” for `disciple` variable is created
	// upon entering scope. TDZ for `disciple` variable begins

	// accessing a variable in TDZ either to get or set
	// is a ReferenceError
	disciple = ‘matthew’;
	console.log(disciple);

	// TDZ ends at declaration and `disciple` is initialized
	// w/ `undefined` value
	let disciple;

	console.log(disciple); // undefined

	disciple = ‘thomas’;
	console.log(disciple); // ‘thomas’
}
```

```javascript
function temporalDeadZoneExample() {
	// TDZ for `value` begins

	const func = function() {
		// Even though this function is defined _before_
		// `value` in the code, it's not called until after
		// `value` is declared, so accessing it is OK.
		console.log('value is: ', value);
	}

	// TDZ for `value` continues. Accessing `value`
	// here would be a ReferenceError. Calling `func`
	// here would cause a ReferenceError.

	// TDZ ends with declaration of `value`
	let value = 'foo';

	// no longer in TDZ when calling function so now
	// any access of `value` is ok
	func();
}
temporalDeadZoneExample();
```
* Destructuring
Destructuring makes it easier to work with objects and arrays in JavaScript. Using a pattern syntax similar to object and array literals, we can poke into data structures and pick out the information we want into variables.

```javascript
// object pattern matching
let {lName, fName} = {fName: 'John', age: 15, lName: 'Doe'};

// array pattern matching
let [first, second, third] = [8, 4, 100, -5, 20];

// output: Doe, John
console.log(lName + ', '+ fName);

// output: 100, 4, 8
console.log(third, second, first);
```

```
