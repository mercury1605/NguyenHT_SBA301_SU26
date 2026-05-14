import { Card, Button } from 'react-bootstrap';
const CustomCard = ({ orchid: { url, name, category } }) => {
    return (
        <>
            <Card className='h-100'>
                <Card.Img variant="top" src={url} style={{
                    height: '200px',
                    objectFit: 'cover'
                }} />
                <Card.Body>
                    <Card.Title>{name}</Card.Title>
                    <Card.Text>
                        Category: {category}
                    </Card.Text>
                    <Button variant="primary">
                        Detail
                    </Button>
                </Card.Body>
            </Card>
        </>
    )
}
export default CustomCard