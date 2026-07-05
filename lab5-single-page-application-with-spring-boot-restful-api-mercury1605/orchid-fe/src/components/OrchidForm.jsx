import { useEffect, useState } from 'react';
import { Button, Col, Form, Image, Row } from 'react-bootstrap';

const EMPTY_FORM = {
  orchidName: '',
  orchidDescription: '',
  orchidCategory: '',
  orchidURL: '',
  isNatural: false,
  isAttractive: false,
};

function OrchidForm({ initialData, onSubmit, submitLabel = 'Save', loading }) {
  const [formData, setFormData] = useState(initialData ?? EMPTY_FORM);

  useEffect(() => {
    setFormData(initialData ?? EMPTY_FORM);
  }, [initialData]);

  const handleChange = (e) => {
    const { name, type, checked, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(formData);
  };

  return (
    <Form onSubmit={handleSubmit}>
      <Row className="mb-3">
        <Form.Group as={Col} md={6}>
          <Form.Label>Orchid Name</Form.Label>
          <Form.Control
            name="orchidName"
            value={formData.orchidName}
            onChange={handleChange}
            placeholder="Enter orchid name"
            required
          />
        </Form.Group>

        <Form.Group as={Col} md={6}>
          <Form.Label>Category</Form.Label>
          <Form.Control
            name="orchidCategory"
            value={formData.orchidCategory}
            onChange={handleChange}
            placeholder="Enter category"
          />
        </Form.Group>
      </Row>

      <Form.Group className="mb-3">
        <Form.Label>Description</Form.Label>
        <Form.Control
          as="textarea"
          rows={3}
          name="orchidDescription"
          value={formData.orchidDescription}
          onChange={handleChange}
          placeholder="Enter description"
        />
      </Form.Group>

      <Form.Group className="mb-3">
        <Form.Label>Image URL</Form.Label>
        <Form.Control
          type="url"
          name="orchidURL"
          value={formData.orchidURL}
          onChange={handleChange}
          placeholder="https://..."
        />
        {formData.orchidURL && (
          <div className="mt-2 border rounded p-2" style={{ width: 'fit-content' }}>
            <Image
              src={formData.orchidURL}
              alt="Preview"
              width={160}
              height={120}
              style={{ objectFit: 'cover' }}
              rounded
              onError={(e) => {
                e.target.style.display = 'none';
              }}
              onLoad={(e) => {
                e.target.style.display = 'block';
              }}
            />
          </div>
        )}
      </Form.Group>

      <Row className="mb-4">
        <Col>
          <Form.Check
            type="checkbox"
            id="isNatural"
            name="isNatural"
            label="Natural"
            checked={formData.isNatural}
            onChange={handleChange}
          />
        </Col>
        <Col>
          <Form.Check
            type="checkbox"
            id="isAttractive"
            name="isAttractive"
            label="Attractive"
            checked={formData.isAttractive}
            onChange={handleChange}
          />
        </Col>
      </Row>

      <Button type="submit" variant="primary" disabled={loading}>
        {loading ? 'Saving...' : submitLabel}
      </Button>
    </Form>
  );
}

export default OrchidForm;
