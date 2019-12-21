import React, { Component } from 'react';
import axios from 'axios';
import SelectPreview from './SelectPreview';
import ProductImageForm from './ProductImageForm';
import ProductAddMessage from './ProductAddMessage';

class ProductForm extends Component {
    state = {
        product: {
            name: '',
            price: '',
            category: '',
            color: [],
            productSize: [],
        },
        colors: [],
        categories: [],
        subCategories: [],
        selectedColor: '',
        selectedSize: '',
        selectedCategory: '',
        selectedSubCategory:'',
        selectedDetailImage: [],
        selectedProductImage: [],
        error: '',
        post: false,
        latestProduct:null,
        productCount:null
    }

    handleSelect = (e) => {
        this.setState({
            [e.target.name]: e.target.value,
            error: ''
        });
        if (e.target.name === "selectedSubCategory") {
            this.setState({
                product: {
                    ...this.state.product,
                    category: this.state.categories[e.target.value]
                }
            });
        }
    }
    handleChange = (e) => {
        this.setState({
            product: {
                ...this.state.product,
                [e.target.name]: e.target.value
            },
            error: ''
        });
    }

    handleAdd = (e) => {
        switch (e.target.name) {
            case "colorBtn":
                const selectedColor = this.state.colors[this.state.selectedColor];
                if (selectedColor) {
                    if (!this.state.product.color.find(i => i.id === selectedColor.id))
                        this.setState({
                            product: {
                                ...this.state.product,
                                color: this.state.product.color.concat(selectedColor)
                            }
                        });
                }
                this.setState({
                    selectedColor: ''
                });
                break;
            case "sizeBtn":
                const selectedSize = { size: this.state.selectedSize.toUpperCase().trim() };
                if (selectedSize.size.length !== 0) {
                    if (!this.state.product.productSize.find(i => i.size === selectedSize.size))
                        this.setState({
                            product: {
                                ...this.state.product,
                                productSize: this.state.product.productSize.concat(selectedSize)
                            },
                            selectedSize: ''
                        })
                }
                this.setState({
                    selectedSize: ''
                });
                break;
            default:
                break;
        }

    }

    handleClick = async () => {
        let flag = true;
        for (let key of Object.keys(this.state.product)) {
            if(this.state.product[key]===undefined || this.state.product[key].length===0){
                flag = false;
                break;
            }
        }
        if (this.state.selectedDetailImage.length === 0 || this.state.selectedProductImage.length === 0){
            flag = false;
        }
        if (flag) {

            let params = new FormData();
            params.append('json', JSON.stringify(this.state.product));
            const file = this.state.selectedDetailImage.concat(this.state.selectedProductImage);
            
            file.forEach((f) => {
                params.append('file', f);
            })
            await axios({
                method: 'post',
                url: '/addProductProcess',
                data: params
            }).then(success => {
                const data=success.data;
                console.log(data);
                this.setState({
                    latestProduct:data.latestProduct.name,
                    productCount:this.state.productCount+data.productCount
                })
            }).catch(
                error => console.log(error)
            );
            this.setState({
                product: {
                    name: '',
                    price: '',
                    category: '',
                    color: [],
                    productSize: []
                },
                subCategories: [],
                selectedColor: '',
                selectedSize: '',
                selectedCategory: '',
                selectedSubCategory:'',
                selectedDetailImage: [],
                selectedProductImage: [],
                error: '',
                post:true
            });
        } else {
            this.setState({
                error: '모든 항목을 입력해주세요.'
            });
        }
    }

    handleDelete = (e) => {
        const name = e.target.getAttribute("name");
        if (name != undefined) {
            this.setState({
                product: {
                    ...this.state.product,
                    [name]: this.state.product[name].filter((i, index) => index !== parseInt(e.target.getAttribute("value")))
                }
            });
        }
    }
    handleFileAdd = (file, stateKey) => {
        this.setState({
            [stateKey]: this.state[stateKey].concat(file),
            error: '',
            post:false
        });
    };

    handleFileRemove = (file, stateKey) => {
        this.setState({
            [stateKey]: this.state[stateKey].filter(f => f.name !== file.name)
        });
    }

    handleSub=(e)=>{
        this.setState({
            selectedCategory:e.target.value,
            subCategories:this.state.categories.filter((c,index)=>{return e.target.value===c.name})
        });
    }

    async getForm() {
        const response = await axios.get("/addProduct");
        const { color, category } = response.data;
        this.setState({
            colors: color,
            categories: category
        });
    }

    componentDidMount() {
        this.getForm();
    }

    render() {
        const { name, price, color, productSize, category } = this.state.product;
        const { colors, categories, subCategories, selectedColor, selectedSize, selectedCategory, selectedSubCategory, error, post, latestProduct, productCount } = this.state;
        const { handleChange, handleClick, handleAdd, handleSelect, handleSub, handleDelete, handleFileAdd, handleFileRemove } = this;
        const colorsOp = colors.map((c, index) => (<option key={c.id} value={index}>{c.color}</option>));
        const categoriesOp = categories.reduce((pre,value)=>{if(!pre.includes(value.name)) pre.push(value.name); return pre;},[]).map((c, index) => (<option key={index} value={c}>{c}</option>));
        const subCategoriesOp=subCategories.map((c, index) => (<option key={c.id} value={index}>{c.subName}</option>));
        return (
            <div className="Form">
                <select name="selectedCategory" value={selectedCategory} onChange={handleSub}>
                    <option value="-1">상위 카테고리 선택</option>
                    {categoriesOp}
                </select>
                <select name="selectedSubCategory" value={selectedSubCategory} onChange={handleSelect}>
                    <option value="-1">하위 카테고리 선택</option>
                    {subCategoriesOp}
                </select>
                <br></br>
                <input name="name" placeholder="name" value={name} onChange={handleChange} /><br></br>
                <input name="price" placeholder="price" value={price} onChange={handleChange} /><br></br>
                <select name="selectedColor" value={selectedColor} onChange={handleSelect}>
                    <option value="-1">색상 선택</option>
                    {colorsOp}
                </select>
                <button name="colorBtn" onClick={handleAdd}>+</button><SelectPreview selects={color} deleteName="color" name="color" onClick={handleDelete} /><br></br>
                <input name="selectedSize" placeholder="size" value={selectedSize} onChange={handleSelect} /><button name="sizeBtn" onClick={handleAdd}>+</button><SelectPreview selects={productSize} deleteName="productSize" name="size" onClick={handleDelete} /><br></br>
                <ProductImageForm onAdd={handleFileAdd} onRemove={handleFileRemove} post={post} stateKey="selectedDetailImage" name="상세 설명 이미지" maxFile="1"></ProductImageForm>
                <ProductImageForm onAdd={handleFileAdd} onRemove={handleFileRemove} post={post} stateKey="selectedProductImage" name="상품 이미지"></ProductImageForm>
                <div>{error}</div>
                <button onClick={handleClick}> 등록</button>
                <ProductAddMessage latestProduct={latestProduct} productCount={productCount}></ProductAddMessage>
            </div>
        );
    };
}

export default ProductForm;