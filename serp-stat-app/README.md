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

### Destructuring

```
/*
 * Destructuring
 */
{
let {lName, fName} = {fName: 'Tom', age: 15, lName: 'Doe'};
// output: Doe, John
console.log(lName + ', '+ fName);
}

/*
 * Object destructuring
 * Object destructuring assignment uses an object literal pattern on the left hand side of an assignment operation. 
 */
{
let config = {delay: 500, title: 'Hi!', info: {name: 'Elijah'}},
{delay, info, title} = config;
// output: {name: 'Elijah'}, 500, 'Hi!'
console.log(info, delay, title);
}

/*
 * Object destructuring
 * We were able to store references to the 3 property values within config into variables with names that matched the property keys of config.
 * This is actually the shorthand syntax for object destructuring 
 */
{
let config = {
		delay: 500,
		title: 'Hi!',
		info: {name: 'Elijah'}
	},
	{
		info: one,
		title: two,
		empty: three,
		delay: four
	} = config;

// output: {name: 'Elijan'}, 'Hi!', undefined, 500
// missing properties have `undefined` value
console.log(one, two, three, four);
}

/*
 * Nested Object destructuring
 */
{
let config = {delay: 500, title: 'Hi!', info: {name: 'Elijah'}},

	// `delay` is using shorthand syntax mixed in w/
	// full syntax
	{
		info: {name: fullName},
		delay,
		title: configTitle
	} = config;

// output: 'Elijah', 'Hi!', 500
console.log(fullName, configTitle, delay);
}

/*
 * Mixed object & array destructuring
 */
{
let json = {
		shapes: ['circle', 'square', 'triangle'],
		colors: 5,
		fill: true,
		author: {
			firstName: 'Ben',
			lastName: 'Ilegbodu',
			city: 'Pittsburg'
		}
	},
	{
  	fill,
    author: {firstName, lastName, city},
		shapes: [, secondShape],
    colors: numColors
	} = json;

// output: true, square, 5
console.log(fill, secondShape, numColors);
// output: Ilegbodu, Ben, Pittsburg
console.log(lastName, firstName, city);
}

/*
 * Destructuring use cases
 * 1. Swapping values
 */
{
let a = 1, b = 2;

[b, a] = [a, b];
}

/*
 * Destructuring use cases
 * 2. Destructuring class objects
 */
{
let {
		protocol: scheme,
		host: domain,
		pathname: path,
		search: query,
		hash,
		href: url
	} = location;

// output: true
console.log(
	(scheme + '//' + domain + path + query + hash) == url
);
}

/*
 * Destructuring use cases
 * 3. Destructuring return values
 */
{
let [, areaCode, exchange, lineNumber] = /^(\d\d\d)-(\d\d\d)-(\d\d\d\d)$/.exec('650-555-1234');

// ["650-555-1234", "650", "555", "1234", index: 0, input: "650-555-1234"]
console.log(/^(\d\d\d)-(\d\d\d)-(\d\d\d\d)$/.exec('650-555-1234'));
// output: 650, 555, 1234
console.log(areaCode, exchange, lineNumber);
}
```

### Parameter Handling

```javascript
/*
 * One rest parameter per function and the last parameter
 */

/*
 * Parameter Handling - optional (ordering)
 */
{

function getData(data, useCache=true, msg) {
	if (useCache) {
		console.log('using cache for', data);
	}
	else {
		console.log('not using cache', data);
	}
}

// `useCache` is missing and is `undefined`.
// therefore `useCache `defaults to `true`
getData({q:'churches+in+Pittsburg'}, undefined, "msg");

}

/*
 * Parameter Handling - merge to a variable which is Array
 */
{

function join(separator, ...values) {
  return values.join(separator);
}

// all of the parameters after the first
// are gathered together into `values`
// which is a true `Array`
// output: "one//two//three"
console.log(join('//', 'one', 'two', 'three'));

}

/*
 * Parameter Handling - array splits to variables
 */
{

function volume(width, length, height) {
	return width * length * height;
};

// the array values are separated into
// separate parameters
// output: 80 (2 * 8 * 5)
console.log(volume(...[2, 8, 5]));

}

/*
 * Parameter Handling - object destructuring with function parameters
 */
{

let ajax = function(url, {method, delay, callback}) {
	console.log(url, method, delay);
	setTimeout(
		() => callback('DONE!'),
		delay
	);
};

// the second parameter to the function
// is an object whose properties are
// destructured to individual variables
// simulating named parameters
ajax(
	'http://api.eventbrite.com/get',
	{
		delay: 2000,
		method: 'POST',
		callback: function(message) {
			console.log(message);
		}
	}
);

}



/**
 * Parameter Handling - required
 * Gets called if a parameter is missing and the expression
 * specifying the default value is evaluated.
 */
{
function throwIfMissing() {
    throw new Error('Missing parameter');
}
function func(requiredParam = throwIfMissing()) {
    // some implementation
}
}
```

## Spread Operator

```javascript
/*
 * Spread Operator
 */
var objectsList = [
	{
		count: 5,
		delay: 2000,
		early: true,
		message: 'Hello'
	},
	{
		early: false
	}
];

function merge(...objects) {
	let masterObj = {};

	// iterate over `objects` merging each
	// into `masterObj` to generate flattened
	// object
	for (let i = 0; i < objects.length; i++) {
		let obj = objects[i];;
		for (let key in obj)
			masterObj[key] = obj[key];
	}

	return masterObj;
}

let merged = merge(...objectsList);

// output:
// {count:5, delay:2000, early:false, message:'Hello'}
console.log(merged);


let mergedObject = merge(
	{count: 10},
	...objectsList,
	{delay: 1500}
);

// output:
// {count:5, delay:1500, early:false, message:'Hello'}
console.log(mergedObject);

let list = [9, 8, 7, 6, 5],
	[first, second, ...rest] = list;

// output: [7, 6, 5], 8, 9
console.log(rest, second, first);
console.log([11, 10, ...list]);
```


