class ApplicationController < ActionController::API
  handle_api_errors()

  def authorize_as_student
    url = ENV['SPRING_APP_URL'] || 'http://localhost:8080'
    token = request.headers.fetch('Authorization', '')
    conn = Faraday.new(
      url: url,
      headers: { 'Content-Type' => 'application/json', 'Authorization' => token }
    )
    logger.info "Fetching from main API with url #{url}, token: #{token}"
    response = conn.get('/api/student/whoami')
    logger.info "Got response status: #{response.status}"
    logger.info "Got response body: #{response.body}"
    if response.status == 200
      @student = JSON.parse(response.body).deep_symbolize_keys
    end
    render json: {message: "Unauthorized"}, status: 401 unless @student
  end
end
