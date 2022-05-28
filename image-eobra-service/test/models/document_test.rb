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
require "test_helper"

class DocumentTest < ActiveSupport::TestCase
  # test "the truth" do
  #   assert true
  # end
end
