# SERP STAT APP

## React Router V4
    * Reference: https://reacttraining.com/react-router/web/example/modal-gallery

## Get Url param
    * Reference: https://egghead.io/lessons/javascript-redux-using-withrouter-to-inject-the-params-into-connected-components


## ES6
    * Reference: https://github.com/benmvp/learning-es6/tree/master/examples/es6

* Arrow Function: http://www.benmvp.com/learning-es6-arrow-functions/
* Block level scoping with let and const: http://www.benmvp.com/learning-es6-block-level-scoping-let-const/
* Destructuring: http://www.benmvp.com/learning-es6-destructuring/
* Parameter Handling: http://www.benmvp.com/learning-es6-parameter-handling/
* Spread Operator: http://www.benmvp.com/learning-es6-parameter-handling/#spread-operator
* Enhanced object literals: http://www.benmvp.com/learning-es6-enhanced-object-literals/
* Template literals & tagged templates: http://www.benmvp.com/learning-es6-template-literals-tagged-templates/
* Promises: http://www.benmvp.com/learning-es6-promises/
* for-of loop: http://www.benmvp.com/learning-es6-for-of-loop/
* Classes: http://www.benmvp.com/learning-es6-classes/
* New Collections: http://www.benmvp.com/learning-es6-new-collections/
* Iterators & Iterables: http://www.benmvp.com/learning-es6-generators-as-iterators/
* Generators as Iterators: http://www.benmvp.com/learning-es6-generators-as-iterators/ 
* 12 tricks for ES6 fun: http://www.benmvp.com/learning-es6-12-tricks-for-es6-fun/

* Modules
* Object.assign()
* Array.from()
* Proxies

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
While rest parameters use the rest operator to combine zero or more parameters into a single array parameter, the spread operator does just the opposite. It separates an array into zero or more parameters.

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

## Enhanced object literals
ECMAScript 6 makes declaring object literals even more succinct by providing shorthand syntax for initializing properties from variables and defining function methods. It also enables the ability to have computed property keys in an object literal definition.

```javascript
/*
 * Enhanced object literals
 * 1. Property value shorthand
 * 2. Computed property keys
 * 3. Method definition shorthand
 */

function getCar(make, model, value) {
	return {
		// with property value shorthand
		// syntax, you can omit the property
		// value if key matches variable
		// name
		make,  // same as make: make
		model, // same as model: model
		value, // same as value: value

		// computed values now work with
		// object literals
		['make' + make]: true,

		// Method definition shorthand syntax
		// omits `function` keyword & colon
		depreciate() {
			this.value -= 2500;
		}
	};
}

let car = getCar('Kia', 'Sorento', 40000);

// output: {
//     make: 'Kia',
//     model:'Sorento',
//     value: 40000,
//     makeKia: true,
//     depreciate: function()
// }
console.log(car);

car.depreciate();

// output: 37500
console.log(car.value);
```

## Template literals & tagged templates

```javascript
/*
 * Template literals & tagged templates
 * 1. Simple template literal. `He said, "It's your fault!"`
 * 2. Template literal interpolation `${variable}`
 * 3. Multi-line template literal
 * 4. Tagged template: String.raw
 */

let firstName = 'Ben',
	lastName = `Ilegbodu`;

// Basic template literal is surrounding by
// backticks so single/double quotes do need
// to be escaped
// output: He said, "It's your fault!"
console.log(`He said, "It's your fault!"`);

// Template literals support interpolation.
// The values within `firstName` and `lastName`
// are substituted into where the tokens are
// output: Name: Ilegbodu, Ben
console.log(`Name: ${lastName}, ${firstName}`);

// Template literals support multi-line strings
// output: This is
// 		multi-line text, so that
//		newline characters are
//
//
//		not needed. All whitespace
//			is respected, including tabs.
//
//
console.log(`This is
	multi-line text, so that
	newline characters are


	not needed. All whitespace
		is respected, including tabs.

`);


let rawString = String.raw`\t\tThis is not a\n multi-line string!`;

// instead of tabs and new lines being in the string,
// the actual escape characters are in the string
// (effectively the backslash is escaped)
// output: \t\tThis is not a\n multi-line string!
console.log(rawString);

let name = 'Ben',

	// no more double escaping and we can use
	// interpolation!
	nameRegExp = new RegExp(String.raw`\(${name}\)`);

console.log(nameRegExp.test('(Ben) Ilegbodu'));



/*
 * Template literals & tagged templates
 * 1. Tagged template: Interpolate
 */
function interpolate(literals, ...substitutions) {
    let interpolation = '';
console.log("INSPECT [literals]");
console.log(literals);
console.log("INSPECT [substitutions]");
console.log(substitutions);
    // loop through based on length of substitutions
    // since its shorter by 1
    for (let i = 0; i < substitutions.length; i++) {
        interpolation += literals[i] + substitutions[i];
    }

    // add the extra literal to the end
    interpolation += literals[literals.length - 1];

    return interpolation;
}

let firstNameOfPerson = 'Ben',
	lastNameOfPerson = `Ilegbodu`;

// output: Name: Ilegbodu, Ben
console.log(interpolate`Name: ${lastNameOfPerson}, ${firstNameOfPerson}`);



/*
 * Template literals & tagged templates
 * 2. Tagged template: Interpolate
 */
String.raw = function(literals, ...substitutions) {
	// literals.raw is an array of raw strings
    let rawLiterals = literals.raw,
    	interpolation = '';

    // loop through based on length of substitutions
    // since its shorter by 1
    for (let i = 0; i < substitutions.length; i++) {
        interpolation += rawLiterals[i] + substitutions[i];
    }

    // add the extra raw literal to the end
    interpolation += rawLiterals[rawLiterals.length - 1];

    return interpolation;
}

// output: \t\tThis 1 is not a\n multi-line string!
console.log(String.raw`\t\tThis ${1} is not a\n multi-line string!`);


```

## Promises

```javascript
/*
 * Promises
 * Instead of registering a callback in the call to an async function, the function returns a promise
 * 1. Event handlers
 * 2. Callbacks
 * 3. Reactive programming
 * 4. Chaining Promises
 * http://www.benmvp.com/learning-es6-promises/
 */

fetch('/json/data.json')
    .then(response => {
    	let data = JSON.parse(response.text);

    	console.log('main data', data);

    	// now call `fetch` again to retrieve new data
    	// based on the response data
    	return fetch(data.url);
    })
    .then(response => {
    	console.log('inner data', response);
    })
    .catch(e => {
    	// catching all failures!
    	console.error(e);
    });
    
```

## for-of operator

```javascript
let list = [8, 3, 11, 9, 6];

for (let value of list) {
  console.log(value);
}
```


## Classes

```javascript
/*
 * Class
 * Instead of registering a callback in the call to an async function, the function returns a promise
 * 1. Event handlers
 * 2. Callbacks
 * 3. Reactive programming
 * 4. Chaining Promises
 * http://www.benmvp.com/learning-es6-promises/
 */

class Pencil {
	constructor(id, content, owner) {
		this.id = id;
		this.content = content;
		this.owner = owner;
	}
}

class ColorPencil extends Pencil {
	constructor(id, content, owner, color='#ff0000') {
		// super constructor must be called first!
		super(id, content, owner);
		this.color = color;
	}
}

let colorPencil = new ColorPencil();
console.log(colorPencil.color);

/*
 * Class - abstract class
 * Unfortunately, ES6 classes don’t yet leverage the abstract keyword to make abstract base classes
 */
class InheritanceError extends Error { }
class Computer {
	  constructor() {
		if (new.target === Computer) {
			throw new InheritanceError('Computer cannot be directly constructed.')
		}
	}
}
class Laptop extends Computer {

}
let computer = new Computer();			   // error!
let laptop = new Laptop();   // ok


/*
 * Class - Instance methods
 */
class ColorPen extends Pen {
	constructor(id, content, owner, color='#ff0000') {
		// super constructor must be called first!
		super(id, content, owner);
		this.color = color;
	}

	// Instance Method
	['to' + 'String']() { // computed method names are supported
		// Override `toString()`, but call
		// parent/super version first
		return `${super.toString()}
			Color: ${this.color}`;
	}
}

/*
 * Class - Static methods
 */
class Note {
	constructor(id, content, owner) {
		this.id = id;
		this.content = content;
		this.owner = owner;
	}

	static add(...properties) {
 
 console.log(properties);
 
		// `this` will be the class on
		// which `add()` was called increment counter
		++this._idCounter;

		let id = `note${this._idCounter}`;

		// construct a new instance of the note passing in the
		// arguments after the ID. This is so subclasses can
		// get all of the arguments needed
		let note = new this(id, ...properties);

		// add note to the lookup by ID
		this._noteLookup[id] = note;

		return note;
	}

	static get(id) {
	  	return this._noteLookup[id];
	}
}

Note._idCounter = -1;
Note._noteLookup = {};

```

## New Collections

```javascript
/*
 * New Collections
 * 1. Set
 * 2. Map
 * 3. WeakSet
 * 4. WeakMap
 * http://www.benmvp.com/learning-es6-promises/
 */

/*
 * Set
 */
let set = new Set([true, 'Ben', 5]);

set.add(false).add('Ilegbodu').add(25).add(true);

// output: 6
console.log(set.size);

// output: true
console.log(set.has('Ben'));

/*
 * Set - dedupe example
 */
function dedupe(array) {
    return [...new Set(array)];
}

let noDupesArray = dedupe([1, 2, 1, 4, 7, 3, 1]);

// output: [1, 2, 4, 7, 3]
console.log(noDupesArray);


/*
 * Set - intersection example
 */
function intersection(setA, setB) {
    return new Set([...setA].filter(item => setB.has(item)));
}

let setIntersection = intersection(
    new Set(['a', 'b', 'c', 'd']),
    new Set(['d', 'e', 'f', 'g'])
);

// output: 1
console.log(setIntersection.size);

/*
 * Set - difference example
 */
function difference(setA, setB) {
    return new Set([...setA].filter(item => !setB.has(item)));
}

let setDifference = difference(
    new Set(['a', 'b', 'c', 'd']),
    new Set(['d', 'e', 'f', 'g'])
);

// output: 3
console.log(setDifference.size);


/*
 * Map
 */
let map = new Map();

map.set('foo', 'bar');
map.set(true, 'Ben'); // non-strings can be keys

// output: Ben
console.log(map.get(true));

// output: 2
console.log(map.size);


class Player {
	constructor(name) {
  	this.name = name;
  }
}

/*
 * Map - Object Key Example
 */
let steph = new Player('Stephen Curry');
let kobe = new Player('Kobe Bryant');
let lebron = new Player('LeBron James');
let allStarVotes = new Map();

allStarVotes.set(steph, 50)
    .set(kobe, 0)
    .set(lebron, 22);

// output: 50
console.log(allStarVotes.get(steph));

// output: false
console.log(allStarVotes.has('Kevin Durant'));

allStarVotes.delete(kobe);

// output: 2
console.log(allStarVotes.size);
for (let [player, count] of allStarVotes) {
    console.log(`${player.name} (${count})`);
}

allStarVotes.clear();

// output: 0
console.log(allStarVotes.size);



/*
 * Map - merge raw data with Map objects
 */
let durant = new Player('Kevin Durant');
let cp3 = new Player('Chris Paul');
let theBrow = new Player('Anthony Davis');

let russell = new Player('Russell Westbrook');
let carmelo = new Player('Carmelo Anthony');

let moreAllStarVotes = new Map([
    [durant, 20],
    [cp3, 5],
    [theBrow, 10]
]);
let rawData = [
    [russell, 12],
    [carmelo, 15]
];

let mergedMap = new Map([...allStarVotes, ...moreAllStarVotes, ...rawData]);
console.log(mergedMap);



/*
 * WeakMap - It’s not iterable. Only accessiable by key
 * NO : .size(), .clear(), .entries(), .keys(), .values() or .forEach()
 */
let stephC = new Player('Stephen Curry');
let kobeB = new Player('Kobe Bryant');
let lebronJ = new Player('LeBron James');
let allStarVotesWeak = new WeakMap();

allStarVotesWeak.set(stephC, 50)
    .set(kobeB, 0)
    .set(lebronJ, 22);

// output: 50
console.log(allStarVotesWeak.get(stephC));

// output: false
console.log(allStarVotesWeak.has('Kevin Durant'));

allStarVotesWeak.delete(kobeB);


/*
 * WeakSet - A WeakSet is basically the combination of a Set and a WeakMap.
 * NO : .size(), .clear(), .entries(), .keys(), .values() or .forEach()
 */

```

## Iterators & Iterable

```javascript
/*
 * Iterator
 */

class MyIterator {
    constructor() {
        this.step = 0;
    }
    next() {
        this.step++;

        if (this.step === 1)
            return {value: 'Ben'};
        else if (this.step == 2)
            return {value: 'Ilegbodu'};

        return {done: true};
    }
}

let myIter = new MyIterator();

// output: {value: 'Ben'}
console.log(myIter.next());

// output: {value: 'Ilegbodu'}
console.log(myIter.next());

// output: {done: true}
console.log(myIter.next());

// output: {done: true}
console.log(myIter.next());

```

## Generators as iterators

```javascript
/*
 * Generators as iterators - basic
 */

function* range(start, count) {
    for (let delta = 0; delta < count; delta++) {
        yield start + delta;
    }
}

for (let teenageYear of range(13, 7)) {
    console.log(`Teenage angst @ ${teenageYear}!`);
}


/*
 * Generators as iterators - a real example
 */
// Enumerable class that wraps an iterator exposing methods
// to lazily transform the items
class Enumerable {
    constructor(iterator) {
        // assuming iterator is some sort of iterable
        this._iterator = iterator;
    }

    *[Symbol.iterator]() {
        yield* this._iterator;
    }

    // Static (and private) helper generator functions
    static *_filter(iterator, predicate) {
        for (let value of iterator) {
            if (predicate(value)) {
                yield value;
            }
        }
    }
    static *_map(iterator, mapperFunc) {
        for (let value of iterator) {
            yield mapperFunc(value);
        }
    }
    static *_take(iterator, count) {
        let index = -1;
        for (let value of iterator) {
            if (++index >= count) {
                break;
            }

            yield value;
        }
    }

    // Instance methods wrapping functional helpers which allow for chaining
    // The existing iterator is transformed by the helper generator function.
    // The operations haven't actually happened yet, just the "instructions"
    filter(predicate) {
        this._iterator = Enumerable._filter(this._iterator, predicate);
        return this;
    }
    map(mapper) {
        this._iterator = Enumerable._map(this._iterator, mapper);
        return this;
    }
    take(count) {
        this._iterator = Enumerable._take(this._iterator, count);
        return this;
    }
}

function generateStocks() {
    // Returns an infinite generator that keeps on returning new stocks
    function* _generate() {
        for (let stockNo = 1; ; stockNo++) {
            let stockInfo = {
                name: `Stock #${stockNo}`,
                price: +(Math.random() * 100).toFixed(2)
            };

            console.log('Generated stock info', stockInfo);

            yield stockInfo;
        }
    }

    return new Enumerable(_generate());
}

let enumerable = generateStocks()
    .filter((stockInfo) => stockInfo.price > 30)
    .map((stockInfo) => `${stockInfo.name} ($${stockInfo.price})`)
    .take(5);

// Even though `_generate()` is an infinite generator, it's also lazy so
// we only look at enough stocks that are > 30 until we get 5 of them
console.log([...enumerable]);

```

