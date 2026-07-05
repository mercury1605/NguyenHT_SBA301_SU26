import { useState } from 'react';
import { Alert, Breadcrumb } from 'react-bootstrap';
import { Link, useNavigate } from 'react-router-dom';
import { useOrchid } from '../context/OrchidContext';
import OrchidForm from '../components/OrchidForm';

export default function AddOrchidPage() {
  const { addOrchid } = useOrchid();
  const navigate = useNavigate();

  const [saving, setSaving] = useState(false);
  const [success, setSuccess] = useState(false);
  const [errorMsg, setErrorMsg] = useState('');

  const handleSubmit = async (data) => {
    setSaving(true);
    setErrorMsg('');
    try {
      await addOrchid(data);
      setSuccess(true);
      setTimeout(() => navigate('/'), 1500);
    } catch (err) {
      setErrorMsg(err.message || 'An error occurred while adding the orchid.');
    } finally {
      setSaving(false);
    }
  };

  return (
    <div style={{ maxWidth: '720px' }} className="mx-auto">
      <Breadcrumb>
        <Breadcrumb.Item linkAs={Link} linkProps={{ to: '/' }}>
          Home
        </Breadcrumb.Item>
        <Breadcrumb.Item active>Add Orchid</Breadcrumb.Item>
      </Breadcrumb>

      <h2 className="mb-4">Add New Orchid</h2>

      {success && (
        <Alert variant="success">
          Orchid added successfully! Redirecting to home...
        </Alert>
      )}

      {errorMsg && <Alert variant="danger">{errorMsg}</Alert>}

      <OrchidForm
        onSubmit={handleSubmit}
        submitLabel="Add Orchid"
        loading={saving}
      />
    </div>
  );
}
