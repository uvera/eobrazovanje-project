# == Schema Information
#
# Table name: documents
#
#  id         :uuid             not null, primary key
#  name       :string           default("")
#  created_at :datetime         not null
#  updated_at :datetime         not null
#  student_id :uuid             not null
#
class Document < ApplicationRecord
  has_many_attached :files
end
