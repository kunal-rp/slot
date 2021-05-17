import React from 'react';
import Button from '@material-ui/core/Button';

const HelloWorld = () => {

	console.log("hello world")

  return (
  		<div>
	     	<h3>Hello this is a test 123 </h3>
	     	<Button variant="contained" color="primary" onClick={() => { console.log('clicked') }}>
		     	Hello World
		   	</Button>
	    </div>
  );
};

export default HelloWorld;