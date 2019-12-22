import React, { Component } from 'react';
import axios from 'axios';

class DeleteProduct extends Component {

    componentDidMount(){
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
        const productId=this.props.match.params.productId;
        this.deleteProduct(productId);
    }


    render() {
        const { handleDelete } = this;
        return (
                <button onClick={handleDelete}>삭제</button>
        );
    }
}

export default DeleteProduct;