import React, { Component } from 'react';

class ProductAddMessage extends Component {

    shouldComponentUpdate(nextProps,nextState){
        return nextProps.latestProduct!==this.props.latestProduct;
    }

    render() {
        const { latestProduct, productCount } = this.props;
        const completeMessage = latestProduct?
                (<div>
                    <img style={{ width: "60px", height: "60px" }} src={latestProduct && "https://storage.googleapis.com/bit-jaehoon/" + latestProduct}></img>
                    <span>{productCount}</span>
                </div>):null;

        return (
            <div className="completeMessage">

                {completeMessage}
            </div>
        );
    }


}

export default ProductAddMessage;