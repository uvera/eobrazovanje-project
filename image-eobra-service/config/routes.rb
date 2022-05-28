Rails.application.routes.draw do
  # Define your application routes per the DSL in https://guides.rubyonrails.org/routing.html

  scope :api do
    resource :document, controller: 'document', only: [:create] do
      get '/me', to: 'document#me'
    end
  end
  # Defines the root path route ("/")
  # root "articles#index"
end
