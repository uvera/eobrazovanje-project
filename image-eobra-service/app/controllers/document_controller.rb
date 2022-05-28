class DocumentController < ApplicationController
  before_action :authorize_as_student, only: [:create, :me]
  before_action do
    ActiveStorage::Current.host = "http://localhost:9000"
  end

  def create
    @document = Document.create!(files: params.require(:files), name: params.require(:name), student_id: @student[:id])
    render 'document/created'
  end

  def me
    @documents = Document.where(student_id: @student[:id])
    render 'document/me'
  end
end
