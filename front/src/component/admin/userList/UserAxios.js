import React, { Component } from 'react'
import axios from 'axios';

export default class UserAxios extends Component {
  state = {
    Users: []
  }
  
  test3() {
    axios.get(`/list1`)
    .then(res => {
      console.log(res);
      const Users = res.data;
      this.setState({ Users });
    })
  }

  componentDidMount() {
    this.test3();
  } 

  render() {
    return (
      <ul>
        {
          this.state.Users.map(user =>
            <li>
              {user.id}, {user.social_Id}, {user.name}, {user.birth}, {user.sex}, {user.height}, {user.weight}, {user.phone}, {user.accrue}, {user.level},
        {user.role}, {user.sigh_Up_Date}, {user.access_Token}, {user.refresh_Token}, {user.total_Emoney} </li>)}
      </ul>
    )
  }
}