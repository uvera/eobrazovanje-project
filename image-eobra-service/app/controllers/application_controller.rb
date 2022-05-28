class ApplicationController < ActionController::API
  handle_api_errors()

  def authorize_as_student
    conn = Faraday.new(
      url: ENV['SPRING_APP_URL'] || 'http://localhost:8080',
      headers: { 'Content-Type' => 'application/json', 'Authorization' => request.headers.fetch('Authorization', '') }
    )
    response = conn.get('/api/student/whoami')
    if response.status == 200
      @student = JSON.parse(response.body).deep_symbolize_keys
    end
    render json: {message: "Unauthorized"}, status: 401 unless @student
  end
end
