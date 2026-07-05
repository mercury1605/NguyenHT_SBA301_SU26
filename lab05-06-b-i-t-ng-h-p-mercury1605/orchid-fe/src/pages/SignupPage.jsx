import { useState } from 'react'
import { Link, Navigate } from 'react-router-dom'
import { Container, Card, Form, Button, Alert } from 'react-bootstrap'
import { useAuth } from '../context/AuthContext'

export default function SignupPage() {
  const { token, signup } = useAuth()
  const [form, setForm] = useState({ fullName:'', email:'', password:'' })
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')

  if (token) return <Navigate to="/" replace />

  const handleChange = e => setForm(prev => ({ ...prev, [e.target.name]: e.target.value }))

  const handleSubmit = async (e) => {
    e.preventDefault()
    if (form.password.length < 8) { setError('Password must be at least 8 characters.'); return }
    setLoading(true); setError('')
    try { await signup(form.fullName, form.email, form.password) }
    catch (err) { setError(err.response?.data?.message ?? 'Sign up failed.') }
    finally { setLoading(false) }
  }

  return (
    <Container className="d-flex justify-content-center align-items-center" style={{ minHeight:'80vh' }}>
      <Card style={{ width:420 }} className="shadow">
        <Card.Body className="p-4">
          <h3 className="text-center mb-4">Sign Up</h3>
          {error && <Alert variant="danger">{error}</Alert>}
          <Form onSubmit={handleSubmit}>
            <Form.Group className="mb-3">
              <Form.Label>Full name</Form.Label>
              <Form.Control name="fullName" value={form.fullName} onChange={handleChange} required />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Email</Form.Label>
              <Form.Control type="email" name="email" value={form.email} onChange={handleChange} required />
            </Form.Group>
            <Form.Group className="mb-4">
              <Form.Label>Password</Form.Label>
              <Form.Control type="password" name="password" placeholder="At least 8 characters"
                value={form.password} onChange={handleChange} required />
            </Form.Group>
            <Button type="submit" variant="success" className="w-100" disabled={loading}>
              {loading ? 'Signing up...' : 'Sign Up'}
            </Button>
          </Form>
          <hr />
          <p className="text-center mb-0">Already have an account? <Link to="/login">Log in</Link></p>
        </Card.Body>
      </Card>
    </Container>
  )
}
