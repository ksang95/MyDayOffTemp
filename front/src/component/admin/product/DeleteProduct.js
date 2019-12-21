import React, { Component } from 'react';
import axios from 'axios';

class DeleteProduct extends Component {
    state = {
        id: ''
    }

    componentDidMount(){
        this.setState({
            id:this.props.id
        })
    }

    async deleteProduct(id) {
        const params = new URLSearchParams();
        params.append('id', id);
        await axios({
            method: 'post',
            url: '/deleteProduct',
            data: params
        });
    }

   
    handleDelete = () => {
        this.deleteProduct(this.state.id);
    }


    render() {
        const { handleDelete } = this;
        return (
                <button onClick={handleDelete}>삭제</button>
        );
    }
}

export default DeleteProduct;