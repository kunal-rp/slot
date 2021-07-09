import React from 'react';
import Button from '@material-ui/core/Button';

const Task = (props) => {

  return (
  		<div>id : {props.task.getStartTimestamp()}</div>
  );
};


export default Task;